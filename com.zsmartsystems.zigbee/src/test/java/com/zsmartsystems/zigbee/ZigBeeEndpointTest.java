/**
 * Copyright (c) 2016-2019 by the respective copyright holders.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.zsmartsystems.zigbee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.zsmartsystems.zigbee.app.ZigBeeApplication;
import com.zsmartsystems.zigbee.database.ZigBeeEndpointDao;
import com.zsmartsystems.zigbee.transaction.ZigBeeTransactionMatcher;
import com.zsmartsystems.zigbee.zcl.ZclCluster;
import com.zsmartsystems.zigbee.zcl.ZclCommand;
import com.zsmartsystems.zigbee.zcl.clusters.ZclAlarmsCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclBasicCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclColorControlCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclCustomCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclDoorLockCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclLevelControlCluster;
import com.zsmartsystems.zigbee.zcl.clusters.ZclScenesCluster;
import com.zsmartsystems.zigbee.zcl.clusters.general.DefaultResponse;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReadAttributesResponse;
import com.zsmartsystems.zigbee.zcl.clusters.general.ReportAttributesCommand;
import com.zsmartsystems.zigbee.zcl.protocol.ZclCommandDirection;

/**
 *
 * @author Chris Jackson
 *
 */
public class ZigBeeEndpointTest {
    private static final int TIMEOUT = 5000;

    @Test
    public void testOutputClusterIds() {
        ZigBeeEndpoint endpoint = getEndpoint();

        List<Integer> clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        clusterIdList.add(ZclColorControlCluster.CLUSTER_ID);
        clusterIdList.add(ZclDoorLockCluster.CLUSTER_ID);
        clusterIdList.add(ZclLevelControlCluster.CLUSTER_ID);
        endpoint.setOutputClusterIds(clusterIdList);

        assertEquals(5, endpoint.getOutputClusterIds().size());

        assertNotNull(endpoint.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID));
        assertTrue(endpoint.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertFalse(endpoint.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isServer());

        assertNotNull(endpoint.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID));
        assertTrue(endpoint.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isClient());
        assertFalse(endpoint.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());

        clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        assertTrue(endpoint.getOutputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertFalse(endpoint.getOutputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());

        assertTrue(endpoint.addOutputCluster(new ZclScenesCluster(endpoint)));
        assertFalse(endpoint.addOutputCluster(new ZclScenesCluster(endpoint)));
        assertTrue(endpoint.getOutputClusterIds().contains(ZclScenesCluster.CLUSTER_ID));

        assertTrue(endpoint.getInputClusterIds().isEmpty());

        System.out.println(endpoint.toString());
    }

    @Test
    public void testInputClusterIds() {
        ZigBeeEndpoint endpoint = getEndpoint();

        List<Integer> clusterIdList = new ArrayList<Integer>();
        clusterIdList.add(ZclAlarmsCluster.CLUSTER_ID);
        clusterIdList.add(ZclBasicCluster.CLUSTER_ID);
        clusterIdList.add(ZclColorControlCluster.CLUSTER_ID);
        clusterIdList.add(ZclDoorLockCluster.CLUSTER_ID);
        clusterIdList.add(ZclLevelControlCluster.CLUSTER_ID);
        clusterIdList.add(0xEEEE);
        endpoint.setInputClusterIds(clusterIdList);

        assertEquals(6, endpoint.getInputClusterIds().size());

        assertNotNull(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID));
        assertFalse(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID).isClient());
        assertTrue(endpoint.getInputCluster(ZclAlarmsCluster.CLUSTER_ID).isServer());

        assertNotNull(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID));
        assertFalse(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID).isClient());
        assertTrue(endpoint.getInputCluster(ZclLevelControlCluster.CLUSTER_ID).isServer());

        assertTrue(endpoint.addInputCluster(new ZclScenesCluster(endpoint)));
        assertFalse(endpoint.addInputCluster(new ZclScenesCluster(endpoint)));
        assertTrue(endpoint.getInputClusterIds().contains(ZclScenesCluster.CLUSTER_ID));

        // Here we check that we can add a cluster if the current cluster is the
        assertNotNull(endpoint.getInputCluster(0xEEEE));
        assertTrue(endpoint.getInputCluster(0xEEEE) instanceof ZclCustomCluster);
        ZclCluster cluster = Mockito.mock(ZclCluster.class);
        Mockito.when(cluster.getClusterId()).thenReturn(0xEEEE);
        assertTrue(endpoint.addInputCluster(cluster));
        assertEquals(cluster, endpoint.getInputCluster(0xEEEE));

        assertTrue(endpoint.getOutputClusterIds().isEmpty());
    }

    @Test
    public void testProfileId() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setProfileId(0x104);
        assertEquals(0x104, endpoint.getProfileId());
    }

    @Test
    public void testDeviceVersion() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setDeviceVersion(123);
        assertEquals(123, endpoint.getDeviceVersion());
    }

    @Test
    public void commandReceived() {
        ZigBeeEndpoint endpoint = getEndpoint();

        ZclCommand command = mockZclCommand(ZclCommand.class);
        endpoint.commandReceived(command);

        List<Integer> clusterIds = new ArrayList<>();
        clusterIds.add(0);
        endpoint.setInputClusterIds(clusterIds);
        endpoint.commandReceived(command);

        command = mockZclCommand(ReportAttributesCommand.class);
        endpoint.commandReceived(command);

        ZigBeeApplication application = Mockito.mock(ZigBeeApplication.class);
        Mockito.when(application.getClusterId()).thenReturn(0);
        endpoint.addApplication(application);
        Mockito.verify(application, Mockito.times(1)).appStartup(ArgumentMatchers.any(ZclCluster.class));

        command = mockZclCommand(ReadAttributesResponse.class);
        endpoint.commandReceived(command);
        Mockito.verify(application, Mockito.times(1)).commandReceived(ArgumentMatchers.any(ZclCommand.class));
    }

    private ZclCommand mockZclCommand(Class<?> clazz) {
        ZclCommand command = (ZclCommand) Mockito.mock(clazz);
        ZigBeeEndpointAddress sourceAddress = new ZigBeeEndpointAddress(1234, 5);
        Mockito.when(command.getSourceAddress()).thenReturn(sourceAddress);
        Mockito.when(command.getClusterId()).thenReturn(0);
        Mockito.when(command.getCommandDirection()).thenReturn(ZclCommandDirection.SERVER_TO_CLIENT);

        return command;
    }

    @Test
    public void sendTransaction() {
        ZigBeeEndpoint endpoint = getEndpoint();

        ZclCommand command = mockZclCommand(DefaultResponse.class);
        ZigBeeTransactionMatcher matcher = Mockito.mock(ZigBeeTransactionMatcher.class);

        endpoint.sendTransaction(command, matcher);
        Mockito.verify(endpoint.getParentNode(), Mockito.timeout(TIMEOUT).times(1)).sendTransaction(command, matcher);

        endpoint.sendTransaction(command);
        Mockito.verify(endpoint.getParentNode(), Mockito.timeout(TIMEOUT).times(1)).sendTransaction(command);
    }

    @Test
    public void testGetDeviceId() {
        ZigBeeEndpoint endpoint = getEndpoint();

        endpoint.setDeviceId(9999);
        assertEquals(9999, endpoint.getDeviceId());
    }

    @Test
    public void setDao() {
        ZigBeeEndpoint endpoint = getEndpoint();

        ZigBeeEndpointDao dao = new ZigBeeEndpointDao();
        dao.setEndpointId(1);
        dao.setDeviceId(2);
        dao.setDeviceVersion(3);
        dao.setProfileId(4);
        endpoint.setDao(dao);
        assertEquals(1, endpoint.getEndpointId());
        assertEquals(2, endpoint.getDeviceId());
        assertEquals(3, endpoint.getDeviceVersion());
        assertEquals(4, endpoint.getProfileId());
    }

    private ZigBeeEndpoint getEndpoint() {
        ZigBeeNode node = Mockito.mock(ZigBeeNode.class);
        Mockito.when(node.getNetworkAddress()).thenReturn(1234);
        return new ZigBeeEndpoint(node, 5);
    }
}

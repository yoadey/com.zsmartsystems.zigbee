package com.zsmartsystems.zigbee.dongle.ember.greenpower;

import com.zsmartsystems.zigbee.dongle.ember.ezsp.structure.EmberApsOption;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspDeserializer;
import com.zsmartsystems.zigbee.dongle.ember.internal.serializer.EzspSerializer;
import java.util.HashSet;
import java.util.Set;

public class EmberGpFrame {

    /**
     * The application profile ID that describes the format of the message.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int profileId;

    /**
     * The cluster ID for this message
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int clusterId;

    /**
     * The source endpoint.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int sourceEndpoint;

    /**
     * The destination endpoint.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int destinationEndpoint;

    /**
     * A bitmask of options.
     * <p>
     * EZSP type is <i>EmberApsOption</i> - Java type is {@link EmberApsOption}
     * Parameter allows multiple options so implemented as a {@link Set}.
     */
    private Set<EmberApsOption> options = new HashSet<EmberApsOption>();

    /**
     * The group ID for this message, if it is multicast mode.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     */
    private int groupId;

    /**
     * The sequence number.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     */
    private int sequence;

    /**
     * Default Constructor
     */
    public EmberGpFrame() {
    }

    public EmberGpFrame(EzspDeserializer deserializer) {
        deserialize(deserializer);
    }

    /**
     * The application profile ID that describes the format of the message.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current profileId as {@link int}
     */
    public int getProfileId() {
        return profileId;
    }

    /**
     * The application profile ID that describes the format of the message.
     *
     * @param profileId the profileId to set as {@link int}
     */
    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    /**
     * The cluster ID for this message
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current clusterId as {@link int}
     */
    public int getClusterId() {
        return clusterId;
    }

    /**
     * The cluster ID for this message
     *
     * @param clusterId the clusterId to set as {@link int}
     */
    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    /**
     * The source endpoint.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current sourceEndpoint as {@link int}
     */
    public int getSourceEndpoint() {
        return sourceEndpoint;
    }

    /**
     * The source endpoint.
     *
     * @param sourceEndpoint the sourceEndpoint to set as {@link int}
     */
    public void setSourceEndpoint(int sourceEndpoint) {
        this.sourceEndpoint = sourceEndpoint;
    }

    /**
     * The destination endpoint.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current destinationEndpoint as {@link int}
     */
    public int getDestinationEndpoint() {
        return destinationEndpoint;
    }

    /**
     * The destination endpoint.
     *
     * @param destinationEndpoint the destinationEndpoint to set as {@link int}
     */
    public void setDestinationEndpoint(int destinationEndpoint) {
        this.destinationEndpoint = destinationEndpoint;
    }

    /**
     * A bitmask of options.
     * <p>
     * EZSP type is <i>EmberApsOption</i> - Java type is {@link EmberApsOption}
     *
     * @return the current options as {@link Set} of {@link EmberApsOption}
     */
    public Set<EmberApsOption> getOptions() {
        return options;
    }

    /**
     * A bitmask of options.
     *
     * @param options the options to add to the {@link Set} as {@link EmberApsOption}
     */
    public void addOptions(EmberApsOption options) {
        this.options.add(options);
    }

    /**
     * A bitmask of options.
     *
     * @param options the options to remove to the {@link Set} as {@link EmberApsOption}
     */
    public void removeOptions(EmberApsOption options) {
        this.options.remove(options);
    }

    /**
     * The group ID for this message, if it is multicast mode.
     * <p>
     * EZSP type is <i>uint16_t</i> - Java type is {@link int}
     *
     * @return the current groupId as {@link int}
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * The group ID for this message, if it is multicast mode.
     *
     * @param groupId the groupId to set as {@link int}
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * The sequence number.
     * <p>
     * EZSP type is <i>uint8_t</i> - Java type is {@link int}
     *
     * @return the current sequence as {@link int}
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * The sequence number.
     *
     * @param sequence the sequence to set as {@link int}
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * Serialise the contents of the EZSP structure.
     *
     * @param serializer the {@link EzspSerializer} used to serialize
     */
    public int[] serialize(EzspSerializer serializer) {
        // Serialize the fields
        serializer.serializeUInt16(profileId);
        serializer.serializeUInt16(clusterId);
        serializer.serializeUInt8(sourceEndpoint);
        serializer.serializeUInt8(destinationEndpoint);
        serializer.serializeEmberApsOption(options);
        serializer.serializeUInt16(groupId);
        serializer.serializeUInt8(sequence);
        return serializer.getPayload();
    }

    /**
     * Deserialise the contents of the EZSP structure.
     *
     * @param deserializer the {@link EzspDeserializer} used to deserialize
     */
    public void deserialize(EzspDeserializer deserializer) {
        // Deserialize the fields
        profileId = deserializer.deserializeUInt16();
        clusterId = deserializer.deserializeUInt16();
        sourceEndpoint = deserializer.deserializeUInt8();
        destinationEndpoint = deserializer.deserializeUInt8();
        options = deserializer.deserializeEmberApsOption();
        groupId = deserializer.deserializeUInt16();
        sequence = deserializer.deserializeUInt8();
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(191);
        builder.append("EmberApsFrame [profileId=");
        builder.append(profileId);
        builder.append(", clusterId=");
        builder.append(clusterId);
        builder.append(", sourceEndpoint=");
        builder.append(sourceEndpoint);
        builder.append(", destinationEndpoint=");
        builder.append(destinationEndpoint);
        builder.append(", options=");
        builder.append(options);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", sequence=");
        builder.append(String.format("%02X", sequence));
        builder.append(']');
        return builder.toString();
    }
}

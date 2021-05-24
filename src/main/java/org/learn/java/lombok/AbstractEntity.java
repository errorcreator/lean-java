package org.learn.java.lombok;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Set;

@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(of = "entityUid")
@ToString
public abstract class AbstractEntity<T> {
    /**
     * Conveys the type of entity this is intended to represent. For example if you are storing an itinerary in an application that uses the Data Service this value could be "Itinerary". This data is
     * passed on to output entity and intended for use by the notifier to distinguish various types of entities, as well as a visual representation of what the output represents.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonInclude(Include.NON_NULL)
    private String entityType;

    /**
     * The primary key for the entity. This is used to lookup the object once it has been created.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    private String entityUid;

    /**
     * A value that represents the unique event used this record. This is used to determine if an event is being delivered more than once for purposes of idempotence. The application is not fully
     * idempot as we do not store every event we ever received, only the last event we recieved along with it's results. This assists in the replay of events. This value is not required. If it is not
     * provided the application will attempt to perform this functionallity in a different way, but it is highly recommended that if you have a unique id for the events you are processing that you
     * include it in this field.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    private String eventKey;

    /**
     * An internal field used for modified idempotence to determine if an entity should re-send it's notification of interest when the same event that was processed the last time is received. This
     * field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    private Boolean eventKeyResultedInNotificationOfInterest;

    /**
     * The data type of the {@link AbstractEntity#revision} to be used in conjunction with the {@link AbstractEntity#revisionSerializedString} to create the {@link AbstractEntity#revision} of the
     * entity being received. This should be the fully qualified class name of the desired type. For example, if the {@link AbstractEntity#revisionSerializedString} represents a long this field would
     * be set to "java.lang.Long". This is used to deserialize to the {@link AbstractEntity#revision} field and is not included in the output entity. The class represented by this value must implement
     * {@link Comparable}.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     * @see AbstractEntity#revision
     * @see AbstractEntity#revisionSerializedString
     */
    private String revisionDataType;

    /**
     * A serialized string representing the value to be used in conjunction with the {@link AbstractEntity#revisionDataType} to create the {@link AbstractEntity#revision} of the entity being received.
     * This can be JSON if the revision is a custom class. This is used to deserialize to the {@link AbstractEntity#revision} field and is not included in the output entity.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     * @see AbstractEntity#revision
     * @see AbstractEntity#revisionDataType
     */
    private String revisionSerializedString;

    /**
     * A {@link Comparable} value used to determine if an event that is received is more recent than previously received events. It is created by deserializing the provided
     * {@link AbstractEntity#revisionSerializedString} into a class represented by the provided {@link AbstractEntity#revisionDataType} and should not be provided directly in the transform. It is
     * serialized to the output for use in downstream systems.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     * @see AbstractEntity#revisionDataType
     * @see AbstractEntity#revisionSerializedString
     * @see Comparable
     */
    private Comparable<T> revision;

    /**
     * The optimistic lock version used by persist layer implementations to determine if an optimistic lock exception has occurred. It is for internal application use only and is not provided on the
     * output. This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    private long optimisticLockVersion = 1;

    /**
     * A number provided to downstream systems representing the unique number of times this entity has changed in order. This field can be used as the revision of the entity represented by it's entity
     * uid as it pertains to this application (independent of the calling systems revision). This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    private Long notificationNumber;

    /**
     * Internal field used to preserve the {@link AbstractEntity#entityType} for customized serialization/deserialization. This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    private String preservedEntityType;

    /**
     * Internal field used to preserve the {@link AbstractEntity#revision} for customized serialization/deserialization. This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    private Comparable<T> preservedRevision;

    /**
     * Internal field used to preserve the {@link AbstractEntity#revisionDataType} for customized serialization/deserialization. This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    private String preservedRevisionDataType;

    /**
     * Internal field used to preserve the {@link AbstractEntity#revisionSerializedString} for customized serialization/deserialization. This field should not be transformed into.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    private String preservedRevisionSerializedString;

    @JsonIgnore
    private Set<String> originalSerializedDomainObjects;

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
        this.preservedEntityType = entityType;
    }

    public String getEntityUid() {
        return entityUid;
    }

    public void setEntityUid(String entityUid) {
        this.entityUid = entityUid;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public Boolean isEventKeyResultedInNotificationOfInterest() {
        return eventKeyResultedInNotificationOfInterest;
    }

    public void setEventKeyResultedInNotificationOfInterest(Boolean eventKeyResultedInNotificationOfInterest) {
        this.eventKeyResultedInNotificationOfInterest = eventKeyResultedInNotificationOfInterest;
    }

    public String getRevisionDataType() {
        return revisionDataType;
    }

    public void setRevisionDataType(String revisionDataType) {
        this.revisionDataType = revisionDataType;
        this.preservedRevisionDataType = revisionDataType;
    }

    public String getRevisionSerializedString() {
        return revisionSerializedString;
    }

    public void setRevisionSerializedString(String revisionSerializedString) {
        this.revisionSerializedString = revisionSerializedString;
        this.preservedRevisionSerializedString = revisionSerializedString;
    }

    public Comparable<?> getRevision() {
        return revision;
    }

    public void setRevision(Comparable<T> revision) {
        this.revision = revision;
        this.preservedRevision = revision;
    }

    public long getOptimisticLockVersion() {
        return optimisticLockVersion;
    }

    public void setOptimisticLockVersion(long optimisticLockVersion) {
        this.optimisticLockVersion = optimisticLockVersion;
    }

    public Long getNotificationNumber() {
        return notificationNumber;
    }

    public void setNotificationNumber(Long notificationNumber) {
        this.notificationNumber = notificationNumber;
    }

    public Set<String> getOriginalSerializedDomainObjects() {
        return originalSerializedDomainObjects;
    }

    public void setOriginalSerializedDomainObjects(Set<String> originalSerializedDomainObjects) {
        this.originalSerializedDomainObjects = originalSerializedDomainObjects;
    }

    /**
     * An abstract method implemented by an actual entity to identify the version of this entity. This is used by the application to determine how to deserialize previously persisted versions of an
     * entity an must be unique across all versions created in the entities lifetime.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    @JsonIgnore
    public abstract long getDomainObjectVersion();



    public DeserializationMethod deserializeRevision(ObjectMapper objectMapper, DeserializationMethod preferedDeserializationMethod) throws TransformFailedException {
        String revisionSerializedStringAttempt1;
        DeserializationMethod deserializationMethodForAttempt1;

        String revisionSerializedStringAttempt2;
        DeserializationMethod deserializationMethodForAttempt2;

        if (preferedDeserializationMethod == DeserializationMethod.WITH_QUOTATIONS) {
            revisionSerializedStringAttempt1 = addQuotations(revisionSerializedString);
            deserializationMethodForAttempt1 = DeserializationMethod.WITH_QUOTATIONS;
            revisionSerializedStringAttempt2 = revisionSerializedString;
            deserializationMethodForAttempt2 = DeserializationMethod.WITHOUT_QUOTATIONS;
        } else {
            revisionSerializedStringAttempt1 = revisionSerializedString;
            deserializationMethodForAttempt1 = DeserializationMethod.WITHOUT_QUOTATIONS;
            revisionSerializedStringAttempt2 = addQuotations(revisionSerializedString);
            deserializationMethodForAttempt2 = DeserializationMethod.WITH_QUOTATIONS;
        }

        try {
            setRevision(attemptToDeserializeRevision(objectMapper, revisionSerializedStringAttempt1));
            return deserializationMethodForAttempt1;
        } catch (IOException ioException) {
            try {
                setRevision(attemptToDeserializeRevision(objectMapper, revisionSerializedStringAttempt2));
                return deserializationMethodForAttempt2;
            } catch (ClassNotFoundException | IOException exception) {
                throw new TransformFailedException(String.format("Could not create"));
            }
        } catch (ClassNotFoundException classNotFoundException) {
            throw new TransformFailedException(String.format("Could not create the class type specifi"));
        }
    }

    @SuppressWarnings("unchecked")
    private Comparable<T> attemptToDeserializeRevision(ObjectMapper objectMapper, String serializedString) throws IOException, JsonParseException, JsonMappingException, ClassNotFoundException {
        return (Comparable<T>) objectMapper.readValue(serializedString, Class.forName(revisionDataType));
    }

    private static String addQuotations(String value) {
        String newValue = StringUtils.removeStart(value, "\"");
        newValue = StringUtils.removeEnd(newValue, "\"");

        return "\"" + newValue + "\"";
    }

    /**
     * Increments the optimistic lock version in preperation for an update.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    public long incrementOptimisticLockVersion() {
        return ++optimisticLockVersion;
    }

    public void determineCurrentNotificationNumber() {
        if (getNotificationNumber() == null) {
            setNotificationNumber(getOptimisticLockVersion());
        }
    }

    /**
     * Increments the notification number for determining notification uniqueness.
     *
     * @author Jason Rueckert
     * @since 1.0.1
     */
    public long incrementNotificationNumber() {
        return ++notificationNumber;
    }

    /**
     * Modifies the state of the object in preparation for persist serialization. Ensures the fields we want on the output are set and the ones we don't want are nulled out.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    public void prepareForPersistSerialization() {
        this.revisionDataType = this.preservedRevisionDataType;
        this.revisionSerializedString = this.preservedRevisionSerializedString;

        this.entityType = null;
        this.revision = null;
    }

    /**
     * Modifies the state of the object in preparation for output serialization to downsteam systems. Ensures the fields we want on the output are set and the ones we don't want are nulled out.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    public void prepareForOutputSerialization() {
        this.entityType = this.preservedEntityType;
        this.revision = this.preservedRevision;

        this.eventKeyResultedInNotificationOfInterest = null;
        this.revisionDataType = null;
        this.revisionSerializedString = null;
    }

    /**
     * An enumeration used for performance enhancements of deserialization of the {@link AbstractEntity#revision} when a custom class is used to represent the revision.
     *
     * @author Jason Rueckert
     * @since 1.0.0
     */
    public static enum DeserializationMethod {
        WITH_QUOTATIONS,
        WITHOUT_QUOTATIONS
    }
}

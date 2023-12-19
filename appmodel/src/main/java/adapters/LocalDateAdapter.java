package adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The date-time formatter used for parsing and formatting {@link OffsetDateTime} objects.
 */
public class LocalDateAdapter extends TypeAdapter<OffsetDateTime> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'HH:mm:ssXXX");


    /**
     * Serializes an {@link OffsetDateTime} object to a JSON string.
     *
     * @param out The JsonWriter to write the serialized data.
     * @param value The {@link OffsetDateTime} object to serialize.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void write(JsonWriter out, OffsetDateTime value) throws IOException {

        if (value!=null) {
            out.value(value.format(formatter));
        }

    }

    /**
     * Deserializes a JSON string to an {@link OffsetDateTime} object.
     *
     * @param in The JsonReader to read the JSON string from.
     * @return The deserialized {@link OffsetDateTime} object.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public OffsetDateTime read(JsonReader in) throws IOException {
        String dateStr =in.nextString();
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(dateStr, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        offsetDateTime = offsetDateTime.withOffsetSameInstant(offsetDateTime.getOffset());
        return offsetDateTime;
    }


    public static void main(String[] args) {
        // Example timestamp string
        String timestampString = "2013-04-04T04:30:39+00:00";

        // Parse the OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(timestampString, DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        // Extract the LocalDateTime part
        offsetDateTime = offsetDateTime.withOffsetSameInstant(offsetDateTime.getOffset());

        System.out.println("read toLocalTine" + offsetDateTime.toLocalDateTime());

        // Format the OffsetDateTime back to the original format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        String formattedTimestamp = offsetDateTime.format(formatter);

        // Display the results
        System.out.println("Original Timestamp: " + timestampString);
        System.out.println("Converted OffsetDateTime: " + offsetDateTime);
        System.out.println("Formatted Timestamp: " + formattedTimestamp);

    }


}
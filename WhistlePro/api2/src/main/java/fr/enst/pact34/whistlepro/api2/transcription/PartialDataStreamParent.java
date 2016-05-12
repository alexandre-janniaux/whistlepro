package fr.enst.pact34.whistlepro.api2.transcription;

import java.util.Objects;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 17/04/16.
 */
public interface PartialDataStreamParent  {
     void newData(PartialDataStreamDest emitter, Object c);
}

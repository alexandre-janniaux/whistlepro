package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;

// TODO: documentation
public class AcquisitionStream 
    extends DataSource<DoubleSignalInterface>
{
    //TODO: implement recording [ACQUISITION]
    //
    //TODO: utiliser un jobs ? ou alors on peut simplement mettre l'enregistrement dans un nouveau thread, et un jobs qui envoie régulièrement les données dans le flux ?
    
}

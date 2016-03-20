package fr.enst.pact34.whistlepro.api.modules;

import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.OutputPolicyInterface;
import fr.enst.pact34.whistlepro.api.common.SchedulingPolicy;
import fr.enst.pact34.whistlepro.api.common.StreamProcessInterface;
import fr.enst.pact34.whistlepro.api.stream.ComputeModuleStream;


public class ClassificationModuleComputer
        implements StreamProcessInterface<DoubleSignal2DInterface,DoubleSignal2DInterface>
{

    @Override
    public DoubleSignal2DInterface process(DoubleSignal2DInterface inputData, OutputPolicyInterface<DoubleSignal2DInterface> outputData) {
        return null;
    }
}

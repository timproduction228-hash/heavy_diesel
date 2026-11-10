package com.heavydiesel.blockentity;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

/** Exposes the fuel tank to pipes and buckets for filling only; nothing can be pumped back out. */
public class FuelInputHandler implements IFluidHandler {
    private final FluidTank tank;

    public FuelInputHandler(FluidTank tank) {
        this.tank = tank;
    }

    @Override
    public int getTanks() {
        return tank.getTanks();
    }

    @Override
    public FluidStack getFluidInTank(int index) {
        return tank.getFluidInTank(index);
    }

    @Override
    public int getTankCapacity(int index) {
        return tank.getTankCapacity(index);
    }

    @Override
    public boolean isFluidValid(int index, FluidStack stack) {
        return tank.isFluidValid(index, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        return tank.fill(resource, action);
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }
}

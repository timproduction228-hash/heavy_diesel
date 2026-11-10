package com.heavydiesel.block;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Hit/collision boxes, authored for an engine that extends towards EAST and rotated for the other facings. */
public class EngineShapes {

    private static final VoxelShape BACK_EAST = Shapes.or(
        Block.box(0, 0, 0, 16, 2, 16),
        Block.box(1, 2, 2, 13, 16, 14),
        Block.box(13, 2, 3, 16, 16, 13));

    private static final VoxelShape FRONT_EAST = Shapes.or(
        Block.box(0, 0, 0, 16, 2, 16),
        Block.box(0, 2, 3, 14, 16, 13),
        Block.box(14, 2, 4, 16, 15, 12));

    public static final Map<Direction, VoxelShape> BACK = build(BACK_EAST);
    public static final Map<Direction, VoxelShape> FRONT = build(FRONT_EAST);

    private static Map<Direction, VoxelShape> build(VoxelShape east) {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        map.put(Direction.EAST, east);
        map.put(Direction.SOUTH, rotate(east, 1));
        map.put(Direction.WEST, rotate(east, 2));
        map.put(Direction.NORTH, rotate(east, 3));
        return map;
    }

    /** Rotates clockwise (seen from above) around the block centre, like blockstate "y": 90. */
    private static VoxelShape rotate(VoxelShape shape, int steps) {
        VoxelShape result = shape;
        for (int i = 0; i < steps; i++) {
            VoxelShape[] acc = {Shapes.empty()};
            result.forAllBoxes((x1, y1, z1, x2, y2, z2) ->
                acc[0] = Shapes.or(acc[0], Shapes.box(1 - z2, y1, x1, 1 - z1, y2, x2)));
            result = acc[0];
        }
        return result;
    }
}

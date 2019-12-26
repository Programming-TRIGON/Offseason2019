package frc.robot.enums;

/**
 * Vision target enum and pipline affilation
 */
public enum Target {
    RocketMiddle(1), RocketSide(0), Feeder(0), CargoShip(0);

    private final int index;

    Target(int index) {
        this.index = index;

    }

    public int getIndex() {
        return index;
    }

}

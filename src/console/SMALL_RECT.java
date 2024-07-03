package console;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class SMALL_RECT extends Structure {
    public short Left;
    public short Top;
    public short Right;
    public short Bottom;

    public static class ByReference extends SMALL_RECT implements Structure.ByReference {}

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("Left", "Top", "Right", "Bottom");
    }
}

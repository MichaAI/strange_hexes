package StrangeHexes.WTFMapGenerator.Shapes;

import arc.util.Log;

public abstract class BaseShape {
    public double Rotation = 0f;
    public float SideLength = 1f;
    public float Offset_x = 0f;
    public float Offset_y = 0f;

    public BaseShape(double rotation, float side_length, float offset_x, float offset_y) {
        Rotation = rotation;
        SideLength = side_length;
        Offset_x = offset_x;
        Offset_y = offset_y;
    };

    public void GenerateAndPlace(float base_offset_x, float base_offset_y) throws BaseShapeGeneratedException {
        Log.err("Base shape cant be generated!");
        throw new BaseShapeGeneratedException();
    }
}

class BaseShapeGeneratedException extends Exception {
    public BaseShapeGeneratedException() {
        super("Base shape cant be generated");
    }
}
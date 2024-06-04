package StrangeHexes.WTFMapGenerator.Shapes;

import static arc.math.Mathf.PI;

public class Triangle extends BaseShape {
    double a = 90.0f;
    double b = 210.0f;
    double c = 330.0f;

    public Triangle(float rotation, float side_length, float offset_x, float offset_y) {
        super(rotation, side_length, offset_x, offset_y);
    }

    @Override
    public void GenerateAndPlace(float global_offset_x, float global_offset_y) {
        double rot_a = a + Rotation;
        double rot_b = b + Rotation;
        double rot_c = c + Rotation;

        double a_x = (Math.cos(rot_a) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_x;
        double a_y = (Math.sin(rot_a) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_y;
        double b_x = (Math.cos(rot_b) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_x;
        double b_y = (Math.sin(rot_b) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_y;
        double c_x = (Math.cos(rot_c) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_x;
        double c_y = (Math.sin(rot_c) * (SideLength / (2 * Math.sin(PI / 3)))) + global_offset_y;
    }
}

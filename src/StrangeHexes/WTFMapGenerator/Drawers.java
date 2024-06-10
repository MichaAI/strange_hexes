package StrangeHexes.WTFMapGenerator;

public class Drawers {
    private static int sign(int x) {
        return Integer.compare(x, 0);
        //возвращает 0, если аргумент (x) равен нулю; -1, если x < 0 и 1, если x > 0.
    }

    public static void drawBresenhamLine(int xstart, int ystart, int xend, int yend) {
        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
        dx = xend - xstart;
        dy = yend - ystart;
        incx = sign(dx);
        incy = sign(dy);
        dx = Math.abs(dx);
        dy = Math.abs(dy);

        if (dx > dy) {
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }

        x = xstart;
        y = ystart;
        err = el / 2;
        LoadHexes.map_generator.setBlock(x, y);


        for (int t = 0; t < el; t++) {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;
                y += incy;
            } else {
                x += pdx;
                y += pdy;
            }

            LoadHexes.map_generator.setBlock(x, y);
        }
    }
}

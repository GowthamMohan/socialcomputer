package saarland.dfki.socialanxietytrainer;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOHelper {

    public static void copyAssetToFile(Context ctx, String assetName, File dst) throws IOException {
        InputStream in = ctx.getResources().getAssets().open(assetName);
        OutputStream out = new FileOutputStream(dst);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }

        in.close();
        out.flush();
        out.close();
    }
}

package io.happylrd.smartassistant.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UtilTools {

    public static void setFont(Context context, TextView textView) {
        Typeface fontType = Typeface
                .createFromAsset(context.getAssets(), "fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }

    public static void putImageToShare(Context context, ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteStream);

        byte[] bytes = byteStream.toByteArray();
        String imgStr = new String(Base64.encodeToString(bytes, Base64.DEFAULT));

        //TODO:constant will be extracted later
        ShareUtils.putString(context, "image_title", imgStr);
    }

    public static void getImageFromShare(Context context, ImageView imageView) {
        String imgStr = ShareUtils.getString(context, "image_title", "");
        if (!imgStr.equals("")) {
            byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            Bitmap bitmap = BitmapFactory.decodeStream(byteStream);
            imageView.setImageBitmap(bitmap);
        }
    }
}

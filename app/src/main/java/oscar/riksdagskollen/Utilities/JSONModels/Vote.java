package oscar.riksdagskollen.Utilities.JSONModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by oscar on 2018-06-16.
 */

public class Vote implements Parcelable {

    public String getId() {
        return id;
    }

    public String getPublicerad() {
        return publicerad;
    }

    public String getUndertitel() {
        return undertitel;
    }

    public String getDokument_url_text() {
        return dokument_url_text;
    }

    public String getDokument_url_html() {
        return dokument_url_html;
    }

    public String getTitel() {
        return titel;
    }

    public String getSummary() {
        return summary;
    }

    public String getNotis() {
        return notis;
    }

    private String id;
    private String publicerad;
    private String undertitel;
    private String dokument_url_text;
    private String dokument_url_html;
    private String titel;
    private String summary;


    private String notis;


    protected Vote(Parcel in) {
        id = in.readString();
        publicerad = in.readString();
        undertitel = in.readString();
        dokument_url_text = in.readString();
        dokument_url_html = in.readString();
        titel = in.readString();
        summary = in.readString();
        notis = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(publicerad);
        dest.writeString(undertitel);
        dest.writeString(dokument_url_text);
        dest.writeString(dokument_url_html);
        dest.writeString(titel);
        dest.writeString(summary);
        dest.writeString(notis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Vote> CREATOR = new Creator<Vote>() {
        @Override
        public Vote createFromParcel(Parcel in) {
            return new Vote(in);
        }

        @Override
        public Vote[] newArray(int size) {
            return new Vote[size];
        }
    };
}
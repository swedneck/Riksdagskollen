package oscar.riksdagskollen.Utilities.JSONModels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by oscar on 2018-06-16.
 * To find url:
 * CurrentNews -> CurrentNewsLinkList -> CurrentNewsLinks -> url
 */

public class CurrentNewsLinkList implements Parcelable {

    @SerializedName("link")
    private CurrentNewsLink link;

    public CurrentNewsLink getLink(){
        return link;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.link, flags);
    }

    protected CurrentNewsLinkList(Parcel in) {
        this.link = in.readParcelable(CurrentNewsLink.class.getClassLoader());
    }

    public static final Parcelable.Creator<CurrentNewsLinkList> CREATOR = new Parcelable.Creator<CurrentNewsLinkList>() {
        @Override
        public CurrentNewsLinkList createFromParcel(Parcel source) {
            return new CurrentNewsLinkList(source);
        }

        @Override
        public CurrentNewsLinkList[] newArray(int size) {
            return new CurrentNewsLinkList[size];
        }
    };
}
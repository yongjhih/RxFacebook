package rx.facebook;

//import com.sromku.simple.fb.entities.Attachment;
//import com.sromku.simple.fb.entities.Photo;
//import com.sromku.simple.fb.entities.Post;
//import com.sromku.simple.fb.listeners.OnAttachmentListener;
//import com.sromku.simple.fb.listeners.OnNewPermissionsListener;
//import com.sromku.simple.fb.listeners.OnPhotoListener;
//import com.sromku.simple.fb.listeners.OnPostsListener;

import com.sromku.simple.fb.entities.*;
import com.sromku.simple.fb.listeners.*;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;

import static com.sromku.simple.fb.entities.Privacy.PrivacySettings.ALL_FRIENDS;
import static com.sromku.simple.fb.entities.Privacy.PrivacySettings.EVERYONE;
import static com.sromku.simple.fb.entities.Privacy.PrivacySettings.SELF;

import rx.schedulers.*;
import rx.Observable;
import rx.functions.*;
import rx.subjects.*;
import rx.Subscriber;
import rx.subscriptions.*;

//import rx.android.schedulers.*;
//import rx.android.app.*;
//import rx.android.view.*;

import java.util.List;
import android.app.Activity;
import android.text.TextUtils;

public class FacebookObservable {

    public static Observable<SimpleFacebook> login(Activity activity) {
        return login(SimpleFacebook.getInstance(activity));
    }

    public static class NotAcceptingPermissionsException extends RuntimeException {
        Permission.Type type;

        public NotAcceptingPermissionsException(Permission.Type type) {
            super();
            this.type = type;
        }

        public Permission.Type getType() {
            return type;
        }

    }

    public static Observable<SimpleFacebook> login(SimpleFacebook simpleFacebook) {
        return Observable.create(sub -> {
            simpleFacebook.login(new OnLoginListener() {
                @Override
                public void onLogin() {
                }

                @Override
                public void onLogin(SimpleFacebook simpleFacebook) {
                    sub.onNext(simpleFacebook);
                    sub.onCompleted();
                }

                @Override
                public void onNotAcceptingPermissions(Permission.Type type) {
                    sub.onError(new NotAcceptingPermissionsException(type));
                }

                @Override
                public void onThinking() {
                    // TODO
                }

                @Override
                public void onFail(String reason) {
                    sub.onError(new RuntimeException(reason));
                }

                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        });
    }

    public static Observable<Photo> getPhotos(Activity activity, String entityId) {
        return getPhotos(SimpleFacebook.getInstance(activity), entityId);
    }

    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook, String entityId) {
        return Observable.<List<Photo>>create(sub -> {
            simpleFacebook.getPhotos(entityId, new OnPhotosListener() {
                @Override
                public void onComplete(List<Photo> photos) {
                    sub.onNext(photos);
                    sub.onCompleted();
                }

                @Override
                public void onThinking() {
                    // TODO
                }

                @Override
                public void onFail(String reason) {
                    sub.onError(new RuntimeException(reason));
                }

                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        }).flatMap(Observable::from);
    }

    public static Observable<Photo> getPhotos(Activity activity) {
        return getPhotos(SimpleFacebook.getInstance(activity));
    }

    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook) {
        return Observable.<List<Photo>>create(sub -> {
            simpleFacebook.getPhotos(new OnPhotosListener() {
                @Override
                public void onComplete(List<Photo> photos) {
                    sub.onNext(photos);
                    if (hasNext()) getNext();
                    else sub.onCompleted();
                }

                @Override
                public void onThinking() {
                    // TODO
                }

                @Override
                public void onFail(String reason) {
                    sub.onError(new RuntimeException(reason));
                }

                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        }).flatMap(Observable::from);
    }

    public static Observable<Post> getPosts(Activity activity) {
        return getPosts(SimpleFacebook.getInstance(activity));
    }

    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook) {
        return Observable.<List<Post>>create(sub -> {
            simpleFacebook.getPosts(Post.PostType.POSTS, new OnPostsListener() {
                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
                @Override
                public void onComplete(List<Post> posts) {
                    sub.onNext(posts);
                    if (hasNext()) getNext();
                    else sub.onCompleted();
                }
            });
        }).flatMap(Observable::from);
    }

    public static Observable<Attachment> getAttachment(Activity activity, Post post) {
        return getAttachment(SimpleFacebook.getInstance(activity), post);
    }

    public static Observable<Attachment> getAttachment(SimpleFacebook simpleFacebook, Post post) {
        if (TextUtils.isEmpty(post.getId())) return Observable.empty();
        return Observable.create(sub -> {
            simpleFacebook.getAttachment(post.getId(), new OnAttachmentListener() {
                @Override
                public void onComplete(Attachment attachment) {
                    sub.onNext(attachment);
                    sub.onCompleted();
                }
                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        });
    }

    public static Observable<Photo> getPhoto(Activity activity, Attachment attachment) {
        return getPhoto(SimpleFacebook.getInstance(activity), attachment);
    }

    public static Observable<Photo> getPhoto(SimpleFacebook simpleFacebook, Attachment attachment) {
        if (TextUtils.isEmpty(attachment.getTarget().getId())) return Observable.empty();

        return Observable.create(sub -> {
            simpleFacebook.getPhoto(attachment.getTarget().getId(), new OnPhotoListener() {
                @Override
                public void onComplete(Photo photo) {
                    sub.onNext(photo);
                    sub.onCompleted();
                }
                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        });
    }
}

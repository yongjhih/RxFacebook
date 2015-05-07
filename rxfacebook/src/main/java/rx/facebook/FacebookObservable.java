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

    public static Observable<Photo> getPhotos(Activity activity) {
        return getPhotos(activity, null);
    }

    public static Observable<Photo> getPhotos(Activity activity, String entityId) {
        return getPhotos(SimpleFacebook.getInstance(activity), entityId);
    }

    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook) {
        return getPhotos(simpleFacebook, null);
    }

    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook, String entityId) {
        if (entityId == null) {
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

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<Post> getPosts(Activity activity) {
        return getPosts(activity, null, null);
    }

    /**
     *
     * @param entityId Profile Event Group Page
     * @param entityId
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, String entityId) {
        return getPosts(activity, entityId, null);
    }

    /**
     *
     * @param activity
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, Post.PostType type) {
        return getPosts(activity, null, type);
    }

    /**
     *
     * @param activity
     * @param entityId Profile Event Group Page
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, String entityId, Post.PostType type) {
        return getPosts(SimpleFacebook.getInstance(activity), entityId, type);
    }

    /**
     *
     * @param simpleFacebook
     * @param entityId Profile Event Group Page
     * @return Observable&lt;Post&gt;
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook, String entityId) {
        return getPosts(simpleFacebook, entityId, null);
    }

    /**
     *
     * @param simpleFacebook
     * @param entityId Profile Event Group Page
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook, String entityId, Post.PostType type) {
        if (type == null) type = Post.PostType.POSTS;
        final Post.PostType finalType = type;

        if (entityId == null) {
            return Observable.<List<Post>>create(sub -> {
                simpleFacebook.getPosts(finalType, new OnPostsListener() {
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

        // assert(finalType != null && entityId != null);
        return Observable.<List<Post>>create(sub -> {
            simpleFacebook.getPosts(entityId, finalType, new OnPostsListener() {
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

    /**
     *
     * @param simpleFacebook
     * @return
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook) {
        return getPosts(simpleFacebook, (Post.PostType) null);
    }

    /**
     *
     * @param simpleFacebook
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook, Post.PostType type) {
        return getPosts(simpleFacebook, null, null);
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
}

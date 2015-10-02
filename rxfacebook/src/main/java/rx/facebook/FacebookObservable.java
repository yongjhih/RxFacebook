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

import rx.android.schedulers.AndroidSchedulers;
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
import java.util.Date;
import android.app.Activity;
import android.text.TextUtils;

public class FacebookObservable {

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<SimpleFacebook> login(Activity activity) {
        return login(SimpleFacebook.getInstance(activity), activity);
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

    /**
     *
     * @param simpleFacebook
     * @return
     */
    public static Observable<SimpleFacebook> login(SimpleFacebook simpleFacebook) {
        return login(simpleFacebook, null);
    }

    public static Observable<SimpleFacebook> login(SimpleFacebook simpleFacebook, Activity activity) {
        return Observable.<SimpleFacebook>create(sub -> {
            if (activity != null) {
                activity.runOnUiThread(() -> {
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
            } else {
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
            }
        //}).subscribeOn(AndroidSchedulers.mainThread());
        });
    }

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<Photo> getPhotos(Activity activity) {
        return getPhotos(activity, null);
    }

    /**
     *
     * @param activity
     * @param entityId Profile Album Event Page
     * @return
     */
    public static Observable<Photo> getPhotos(Activity activity, String entityId) {
        return getPhotos(SimpleFacebook.getInstance(activity), entityId, activity);
    }

    /**
     *
     * @param simpleFacebook
     * @return
     */
    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook) {
        return getPhotos(simpleFacebook, null);
    }

    /**
     *
     * @param simpleFacebook
     * @param entityId Profile Album Event Page
     * @return
     */
    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook, String entityId) {
        return getPhotos(simpleFacebook, entityId, null);
    }

    public static Observable<Photo> getPhotos(SimpleFacebook simpleFacebook, String entityId, Activity activity) {
        if (entityId == null) {
            if (activity != null) {
                return Observable.<List<Photo>>create(sub -> {
                    activity.runOnUiThread(() -> {
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
                    });
                }).flatMap(Observable::from);
            } else {
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
                }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
            }
        }

        if (activity != null) {
            return Observable.<List<Photo>>create(sub -> {
                activity.runOnUiThread(() -> {
                    simpleFacebook.getPhotos(entityId, new OnPhotosListener() {
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
                });
            }).flatMap(Observable::from);
        } else {
            return Observable.<List<Photo>>create(sub -> {
                simpleFacebook.getPhotos(entityId, new OnPhotosListener() {
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
            }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<Photo> getUploadedPhotos(Activity activity) {
        return getUploadedPhotos(SimpleFacebook.getInstance(activity));
    }

    /**
     *
     * @param simpleFacebook
     * @return
     */
    public static Observable<Photo> getUploadedPhotos(SimpleFacebook simpleFacebook) {
        return Observable.<List<Photo>>create(sub -> {
            simpleFacebook.getUploadedPhotos(new OnPhotosListener() {
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
        }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     * @param activity
     * @param attachment
     * @return
     */
    public static Observable<Photo> getPhoto(Activity activity, Attachment attachment) {
        return getPhoto(SimpleFacebook.getInstance(activity), attachment, activity);
    }

    /**
     *
     * @param simpleFacebook
     * @param attachment
     * @return
     */
    public static Observable<Photo> getPhoto(SimpleFacebook simpleFacebook, Attachment attachment) {
        return getPhoto(simpleFacebook, attachment, null);
    }

    public static Observable<Photo> getPhoto(SimpleFacebook simpleFacebook, Attachment attachment, Activity activity) {
        if (TextUtils.isEmpty(attachment.getTarget().getId())) return Observable.empty();

        if (activity != null) {
        return Observable.<Photo>create(sub -> {
            activity.runOnUiThread(() -> {
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
        }).onErrorResumeNext(e -> {
            android.util.Log.e("RxFacebook", "" + e);
            e.printStackTrace();
            return Observable.empty();
        });
        } else {
        return Observable.<Photo>create(sub -> {
            activity.runOnUiThread(() -> {
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
        }).subscribeOn(AndroidSchedulers.mainThread());
        }
    }

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<Post> getPosts(Activity activity) {
        return getPosts(activity, (String) null, (Post.PostType) null, (Date) null, (Date) null);
    }

    /**
     *
     * @param entityId Profile Event Group Page
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, String entityId) {
        return getPosts(activity, entityId, (Post.PostType) null, (Date) null, (Date) null);
    }

    /**
     *
     * @param activity
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, Post.PostType type) {
        return getPosts(activity, (String) null, type, (Date) null, (Date) null);
    }

    public static Observable<Post> getPosts(Activity activity, Date since, Date until) {
        return getPosts(activity, (String) null, (Post.PostType) null, since, until);
    }

    /**
     *
     * @param activity
     * @param entityId Profile Event Group Page
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(Activity activity, String entityId, Post.PostType type, Date since, Date until) {
        return getPosts(SimpleFacebook.getInstance(activity), entityId, type, activity, since, until);
    }

    /**
     *
     * @param simpleFacebook
     * @param entityId Profile Event Group Page
     * @return Observable&lt;Post&gt;
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook, String entityId) {
        return getPosts(simpleFacebook, entityId, (Post.PostType) null, (Activity) null, (Date) null, (Date) null);
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
        return getPosts(simpleFacebook, (String) null, (Post.PostType) null, (Activity) null, (Date) null, (Date) null);
    }

    /**
     *
     * @param simpleFacebook
     * @param entityId Profile Event Group Page
     * @param type
     * @return
     */
    public static Observable<Post> getPosts(SimpleFacebook simpleFacebook, String entityId, Post.PostType type, Activity activity, Date since, Date until) {
        if (type == null) type = Post.PostType.POSTS;
        final Post.PostType finalType = type;

        if (entityId == null) {
            if (activity != null) {
                return Observable.<List<Post>>create(sub -> {
                    activity.runOnUiThread(() -> {
                        simpleFacebook.getPosts(finalType, new OnPostsListener() {
                            @Override
                            public void onComplete(List<Post> posts) {
                                try {
                                    sub.onNext(posts);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    sub.onError(e);
                                }
                                try {
                                    if (hasNext()) getNext();
                                    else sub.onCompleted();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    sub.onCompleted();
                                }
                            }

                            @Override
                            public void onException(Throwable throwable) {
                                sub.onError(throwable);
                            }
                        }, since, until);
                    });
                }).flatMap(Observable::from);
            } else {
                return Observable.defer(() -> Observable.<List<Post>>create(sub -> {
                    simpleFacebook.getPosts(finalType, new OnPostsListener() {
                        @Override
                        public void onComplete(List<Post> posts) {
                            try {
                                sub.onNext(posts);
                            } catch (Exception e) {
                                e.printStackTrace();
                                sub.onError(e);
                            }
                            try {
                                if (hasNext()) getNext();
                                else sub.onCompleted();
                            } catch (Exception e) {
                                e.printStackTrace();
                                sub.onCompleted();
                            }
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            sub.onError(throwable);
                        }
                    }, since, until);
                }).flatMap(Observable::from)).subscribeOn(AndroidSchedulers.mainThread());
            }
        }

        // assert(finalType != null && entityId != null);
        return Observable.defer(() -> Observable.<List<Post>>create(sub -> {
            simpleFacebook.getPosts(entityId, finalType, new OnPostsListener() {
                @Override
                public void onComplete(List<Post> posts) {
                    sub.onNext(posts);
                    if (hasNext()) getNext();
                    else sub.onCompleted();
                }

                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            }, since, until);
        }).flatMap(Observable::from)).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     *
     * @param activity
     * @param post
     * @return
     */
    public static Observable<Attachment> getAttachment(Activity activity, Post post) {
        return getAttachment(SimpleFacebook.getInstance(activity), post, activity);
    }

    /**
     *
     * @param simpleFacebook
     * @param post
     * @return
     */
    public static Observable<Attachment> getAttachment(SimpleFacebook simpleFacebook, Post post) {
        return getAttachment(simpleFacebook, post, null);
    }

    public static Observable<Attachment> getAttachment(SimpleFacebook simpleFacebook, Post post, Activity activity) {
        if (TextUtils.isEmpty(post.getId())) return Observable.empty();

            if (activity != null) {
                return Observable.<Attachment>create(sub -> {
                        activity.runOnUiThread(() -> {
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
                    });
            } else {
                return Observable.<Attachment>create(sub -> {
                        activity.runOnUiThread(() -> {
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
                    }).subscribeOn(AndroidSchedulers.mainThread());
            }
    }

    /**
     *
     * @param activity
     * @return
     */
    public static Observable<Account> getAccounts(Activity activity) {
        return getAccounts(SimpleFacebook.getInstance(activity));
    }

    /**
     * Get pages of which the current user is an admin.
     * @param simpleFacebook
     * @return
     */
    public static Observable<Account> getAccounts(SimpleFacebook simpleFacebook) {
        return Observable.<List<Account>>create(sub -> {
            simpleFacebook.getAccounts(new OnAccountsListener() {
                @Override
                public void onComplete(List<Account> accounts) {
                    sub.onNext(accounts);
                    if (hasNext()) getNext();
                    else sub.onCompleted();
                }
                @Override
                public void onException(Throwable throwable) {
                    sub.onError(throwable);
                }
            });
        }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Get all albums
     * @param activity
     * @return
     */
    public static Observable<Album> getAlbums(Activity activity) {
        return getAlbums(activity, null);
    }

    /**
     * Get all albums
     * @param activity
     * @param entityId Profile Page
     * @return
     */
    public static Observable<Album> getAlbums(Activity activity, String entityId) {
        return getAlbums(SimpleFacebook.getInstance(activity), entityId);
    }

    /**
     * Get all albums
     * @param simpleFacebook
     * @return
     */
    public static Observable<Album> getAlbums(SimpleFacebook simpleFacebook) {
        return getAlbums(simpleFacebook, null);
    }

    /**
     * Get all albums
     * @param simpleFacebook
     * @param entityId Profile Page
     * @return
     */
    public static Observable<Album> getAlbums(SimpleFacebook simpleFacebook, String entityId) {
        if (entityId == null) {
            return Observable.<List<Album>>create(sub -> {
                simpleFacebook.getAlbums(new OnAlbumsListener() {
                    @Override
                    public void onComplete(List<Album> albums) {
                        sub.onNext(albums);
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
            }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
        }

        return Observable.<List<Album>>create(sub -> {
            simpleFacebook.getAlbums(entityId, new OnAlbumsListener() {
                @Override
                public void onComplete(List<Album> albums) {
                    sub.onNext(albums);
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
        }).flatMap(Observable::from).subscribeOn(AndroidSchedulers.mainThread());
    }

    /*
    public static Observable<Album> getAlbum(Activity activity) {
        return getAlbum(activity, null);
    }
    */

    /**
     * Get one album
     * @param activity
     * @param albumId
     * @return
     */
    public static Observable<Album> getAlbum(Activity activity, String albumId) {
        return getAlbum(SimpleFacebook.getInstance(activity), albumId);
    }

    /*
    public static Observable<Album> getAlbum(SimpleFacebook simpleFacebook) {
        return getAlbum(simpleFacebook, null);
    }
    */

    /**
     * Get one album
     * @param simpleFacebook
     * @param albumId
     * @return
     */
    public static Observable<Album> getAlbum(SimpleFacebook simpleFacebook, String albumId) {
        return Observable.create(sub -> {
            simpleFacebook.getAlbum(albumId, new OnAlbumListener() {
                @Override
                public void onComplete(Album album) {
                    sub.onNext(album);
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
        });
    }

    //public void requestNewPermissions(Permission[] permissions, boolean showPublish, OnNewPermissionsListener onNewPermissionsListener) {
    public static Observable<String> requestNewReadPermissions(SimpleFacebook simpleFacebook, Activity activity, /* @ReadPermissions */ final Permission[] permissions) {
        // TODO: check non-publish permissions
        return requestNewPermissions(simpleFacebook, activity, permissions, false);
    }

    public static Observable<String> requestNewPublishPermissions(SimpleFacebook simpleFacebook, Activity activity, /* @Permissions */ final Permission[] permissions) {
        return requestNewPermissions(simpleFacebook, activity, permissions, true);
    }

    public static class RejectException extends RuntimeException {
        private Permission.Type type;

        RejectException(Permission.Type type) {
            this.type = type;
        }

        public Permission.Type type() {
            return type;
        }
    }

    private static Observable<String> requestNewPermissions(SimpleFacebook simpleFacebook, Activity activity, final Permission[] permissions, boolean showPublish) {
        return Observable.create(sub -> {
            simpleFacebook.requestNewPermissions(permissions, showPublish,
                    new OnNewPermissionsListener() {
                        @Override
                        public void onSuccess(String accessToken, List<Permission> declinedPermissions) {
                            sub.onNext(accessToken);
                        }

                        @Override
                        public void onNotAcceptingPermissions(Permission.Type type) {
                            sub.onError(new RejectException(type));
                        }

                        @Override
                        public void onThinking() {
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            sub.onError(throwable);
                        }

                        @Override
                        public void onFail(String reason) {
                            sub.onError(new RuntimeException(reason));
                        }
                    });
        });
    }
}

# RxFacebook

[![rxparse.png](art/rxfacebook.png)](art/rxfacebook.png)

<!--
[![rxfacebook.svg](art/rxfacebook.svg)](art/rxfacebook.svg)
-->

Facebook in RxJava. Based on android-simple-facebook.

## Usage

```java
FacebookObservable.login(activity).subscribe();
FacebookObservable.logout(activity).subscribe();
```

```java
Observable<Photo> photos = FacebookObservable.getPhotos(activity);
// entify: Profile Album Event Page
Observable<Photo> photos = FacebookObservable.getPhotos(entityId, activity);
```

```java
Observable<Post> posts = FacebookObservable.getPosts(activity);
// entity: Profile Event Group Page
Observable<Post> posts = FacebookObservable.getPosts(entifyId, activity);
// status: links, statuses, posts or tagged
Observable<Post> posts = FacebookObservable.getPosts(entifyId, PostType.STATUSES, activity);
```

```java
Observable<Account> accounts = FacebookObservable.getAccounts(activity);

```

```java
Observable<Album> albums = FacebookObservable.getAlbums(activity);
// entity: Profile Page
Observable<Album> albums = FacebookObservable.getAlbums(entityId, activity);
Observable<Album> album = FacebookObservable.getAlbum(albumId, activity);

```

```java
// entity: Album Checkin Comment Photo Post Video
Observable<Comment> comments = FacebookObservable.getComments(entityId, activity);
Observable<Comment> comment = FacebookObservable.getComments(commentId, activity);

```

```java
Observable<Event> events = FacebookObservable.getEvents(EventDecision.ATTENDING, activity);
// entity: Profile, Page, Group
Observable<Event> events = FacebookObservable.getEvents(entityId, EventDecision.ATTENDING, activity);

```

```java
Observable<FamilyUser> familyUsers = FacebookObservable.getFamily(activity);
Observable<FamilyUser> familyUsers = FacebookObservable.getFamily(profileId, activity);

```

```java
Observable<Profile> friends = FacebookObservable.getFriends(activity);

```

```java
Observable<Group> groups = FacebookObservable.getGroups(activity);
// entity: Profile
Observable<Group> groups = FacebookObservable.getGroups(entityId, activity);

```

```java
// entity: Album Checkin Comment Photo Post Video
Observable<Like> likes = FacebookObservable.getLikes(entityId, activity);
```

## Installation

```gradle
repositories {
    maven {
        url "https://jitpack.io"
    }
}

dependencies {
    compile 'com.github.yongjhih:rxfacebook:1.0.0'
}
```

## LICENSE

Copyright 2015 8tory, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

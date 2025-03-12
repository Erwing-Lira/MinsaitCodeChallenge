# MinsaitCodeChallenge

## Architecture
![architecture](https://github.com/user-attachments/assets/59bd38ff-c903-45f6-869f-8dff2a574a3a)

## To be able to run
Put in the local.properties file the two parameters, the first one is the api key and the second de account_id
```
TMDB_TOKEN=String
ACCOUNT_ID=String
```

## Finish modules (activities)
1. feature_movie
2. feature_profile 
3. feature_sync
4. feature_map
5. feature_upload

## Video Evidences
Flow between these two features (profile and movie)
[Ver video 1](evidences/fetchData.webm)
[Ver video 2](evidences/getLocalData.webm)

## Image Evidences

1. feature_movie
[Movie Part](evidences/movie.png)

![movie](https://github.com/user-attachments/assets/e9788fdc-acc3-42e9-987b-a0e25cb16319)

2. feature_profile
[Profile Part](evidences/profile.png)

![profile](https://github.com/user-attachments/assets/2aff96b5-b553-4ded-8116-a011776818e8)

3. feature_sync
[Sync Location in Firebase](evidences/Firestore-Locations.png)
[Notification](evidences/Notification.png)

<img width="1144" alt="Firestore-Locations" src="https://github.com/user-attachments/assets/699d059e-d786-4441-a53e-98bb6972a87d" />

<img width="325" alt="Notification" src="https://github.com/user-attachments/assets/dc70766d-3357-4aad-9318-cd09f60b5a30" />

4. feature_map
[Map Big](evidences/MapBig.png)
[Map With TimeStamp](evidences/MapDetail.png)

<img width="326" alt="MapBig" src="https://github.com/user-attachments/assets/e71101f9-b531-4c76-8c1d-e1d8d41d3f73" />

<img width="324" alt="MapDetail" src="https://github.com/user-attachments/assets/c9145b3c-dbd5-4c8b-88a6-070de528651e" />

5. feature_upload

I finished this module, but I couldn't verify that the photos were uploaded, since my Google account requires me to purchase a plan to be able to use this feature, so I'm uploading evidence of this.

The documentation also indicates this plan:
Make sure your Firebase project is on the pay-as-you-go Blaze pricing plan. If you're new to Firebase and Google Cloud, check if you're eligible for a $300 credit.
[Firebase Storage](https://firebase.google.com/docs/storage/android/start)

[Storage price](evidences/storage.png)

<img width="555" alt="storage" src="https://github.com/user-attachments/assets/fea21287-2db4-4c50-82a1-949494c60079" />

## Opportunities to Improve
- Integrate unit testing
- Create a custom RecyclerView to avoid boilerplate
- Using WorkManager the minimum time (intervals) to send information or execute this manager is at least 15 minutes
- Improve to manage the permission in the feature_sync to get current Location and show a Notification

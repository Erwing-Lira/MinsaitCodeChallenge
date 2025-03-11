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

# Pending modules (activities)
1. feature_upload
2. feature_map

## Video Evidences
Flow between these two features (profile and movie)
[Ver video 1](evidences/fetchData.webm)
[Ver video 2](evidences/getLocalData.webm)

## Image Evidences
1. feature_profile
[Profile Part](evidences/profile.png)

![profile](https://github.com/user-attachments/assets/2aff96b5-b553-4ded-8116-a011776818e8)

3. feature_movie
[Movie Part](evidences/movie.png)

![movie](https://github.com/user-attachments/assets/e9788fdc-acc3-42e9-987b-a0e25cb16319)

4. feature_sync
[Sync Location in Firebase](evidences/Firestore-Locations.png)

<img width="1144" alt="Firestore-Locations" src="https://github.com/user-attachments/assets/699d059e-d786-4441-a53e-98bb6972a87d" />


## Opportunities to Improve
- Integrate unit testing
- Create a custom RecyclerView to avoid boilerplate
- Using WorkManager the minimum time (intervals) to send information or execute this manager is at least 15 minutes

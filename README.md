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
2. feature_movie
[Movie Part](evidences/movie.png)
3. feature_sync
[Sync Location in Firebase](evidences/Firestore-Locations.png)

## Opportunities to Improve
- Integrate unit testing
- Create a custom RecyclerView to avoid boilerplate
- Delete UseCases because the are no business logic in this part

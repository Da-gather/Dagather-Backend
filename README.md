# Dagather-Backend
### [다문화 가정을 위한 친구 사귀기 플랫폼]
다게더는 다문화 가정의 정보 취약, 외로움, 한국 생활 적응의 어려움 등의 문제점에서 출발하여, <br>
다문화 가정 간의 커뮤니티 활성화를 위한 친구 사귀기 플랫폼을 제공합니다.
<img width="631" alt="Screenshot 2023-05-25 at 6 37 20 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/fb6c1db1-2154-4dc3-b070-945ce59415e4">
<br><br>


## Key Features
- **친구 추천**

  가입 목적, 관심사, 거리, 한국거주기간 등을 기반으로 홈 화면에서 친구를 추천순으로 제공합니다.

- **맞춤법 검사와 번역**
  
  친구와 채팅을 하며 한국어 맞춤법 검사 기능을 통해 한국어 공부를 할 수 있습니다. <br>
  다른 국적의 친구와 원활한 소통을 위해 번역 기능을 제공합니다.
 
- **미션**
  
  친구와 더 친해지고, 한국과도 더 친해질 수 있도록 다양한 미션을 제공합니다. <br>
  친구와 함께 미션을 수행할 수 있고, 마이페이지에서 완료한 미션을 한눈에 확인할 수 있습니다.
  
<br><br>

## ERD
<img width="999" alt="Screenshot 2024-02-11 at 6 46 37 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/ba875055-e518-45c3-8202-0ae3dd011ebb">
<br><br>

## API 명세
| 기능              | 설명                          |
| ----------------- | ----------------------------- |
| 👫 친구 | 친구 신청 및 수락, 친구 삭제, 친구 목록 조회, 친구 신청 대기 목록 조회 |
| 📝 미션 | 랜덤 미션 생성, 미션 완료, 미션 조회, 미션 통계 조회 |
| 🗣️ 채팅 | 맞춤법 검사 |
| 🔍 프로필 | 프로필 생성, 수정, 조회 |

<br><br>

## 친구 추천 알고리즘
콘텐츠 기반 필터링을 활용하여 사용자가 선택한 취미와 유사한 취미를 가진 사용자를 서로에게 추천해줍니다.

1. CountVectorizer를 사용해 동일한 속성 개수를 세고, 코사인 유사도를 계산합니다.
```python
# CountVectorizer
count_vect = CountVectorizer(min_df=0, ngram_range=(1, 1))
purpose_mat = count_vect.fit_transform(df['purpose'])
interest_mat = count_vect.fit_transform(df['interest'])

# calculate cosine similarity
purpose_sim = cosine_similarity(purpose_mat, purpose_mat)
interest_sim = cosine_similarity(interest_mat, interest_mat)
```

2. 사용자의 가입 목적에 따라 가입목적/취미/거리의 가중치를 달리하여 최종 유사도를 도출합니다.
```python
if '친목' in my_purpose:
    minmax_scaler = MinMaxScaler()
    my_lat = df.loc[profile_idx]['latitude']
    my_long = df.loc[profile_idx]['longitude']
    df['distance'] = df.apply(lambda x : sqrt((my_lat - x['latitude'])**2 + (my_long - x['longitude'])**2), axis=1)
    df['distance_similarity'] = (1 - minmax_scaler.fit_transform(df[['distance']]))
    df['similarity'] = 0.1*df['purpose_similarity'] + 0.6*df['interest_similarity'] + 0.3*df['distance_similarity']
    df = df.sort_values(by="similarity", ascending=False)
    return df['id'].to_list()

elif '한국생활적응' in my_purpose:
    minmax_scaler = MinMaxScaler()
    df['rperiod_similarity'] = minmax_scaler.fit_transform(df[['rperiod']])
    df['rperiod_similarity'].apply(lambda x : 1 - x)
    df['similarity'] = 0.2*df['purpose_similarity'] + 0.2*df['interest_similarity'] + 0.6*df['rperiod_similarity']
    df = df.sort_values(by="similarity", ascending=False)
    return df['id'].to_list()

elif '육아정보공유' in my_purpose or '한국어공부' in my_purpose:
    df['similarity'] = 0.2*df['purpose_similarity'] + 0.8*df['interest_similarity']
    df = df.sort_values(by="similarity", ascending=False)
    return df['id'].to_list()
```

# How to Start
### Installation
Git clone or download zip file

### Execution
1. build project
```
./gradlew build
```
2. run jar file
```
java -jar dagather-0.0.1-SNAPSHOT.jar
```
3. or you can just run application at intellij or STS

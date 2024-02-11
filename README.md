# [다문화 가정을 위한 친구 사귀기 플랫폼]
다게더는 다문화 가정의 정보 취약, 외로움, 한국 생활 적응의 어려움 등의 문제점에서 출발하여, <br>
다문화 가정 간의 커뮤니티 활성화를 위한 친구 사귀기 플랫폼을 제공합니다.
![image](https://github.com/Da-gather/Dagather-Backend/assets/79203421/7021c03c-9e73-4507-88c6-9ba7ff9d1fa4)

<br><br>

## 프로젝트 기간

- 2023.04.01 ~ 2023.05.25

<br>


## 팀원소개
| [윤지애](https://github.com/jiaeYoon) | [김민지](https://github.com/Lightieey)|
| :-----------------------------------: | :-----------------------------------: |
|<img src="https://avatars.githubusercontent.com/u/68904755?v=4" width="100">|<img src="https://avatars.githubusercontent.com/u/79203421?v=4" width="100">|
|                  BE                   |                  BE                  |

<br><br>

## 주요 기능
- **친구 추천**

  가입 목적, 관심사, 거리, 한국거주기간 등을 기반으로 홈 화면에서 친구를 추천순으로 제공합니다.
  
  <img width="40%" alt="Screenshot 2024-02-11 at 7 34 41 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/3fdd8f42-62ef-481b-b036-2614fda4418d">
  <img width="40%" alt="Screenshot 2024-02-11 at 7 34 50 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/374bd4a4-f99a-4e39-8102-a25999c3c4d7">
  <img width="40%" alt="Screenshot 2024-02-11 at 7 35 12 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/c56d34cb-adee-4ba5-891e-877f19febf41">
  <img width="40%" alt="Screenshot 2024-02-11 at 7 35 01 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/c975e556-3361-44c4-8d06-93727f2cdc14">
  

- **맞춤법 검사와 번역**
  
  친구와 채팅을 하며 한국어 맞춤법 검사 기능을 통해 한국어 공부를 할 수 있습니다. <br>
  다른 국적의 친구와 원활한 소통을 위해 번역 기능을 제공합니다.

  <img width="30%" alt="Screenshot 2024-02-11 at 7 35 25 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/a725c077-493c-4e6e-a4bc-98126acd7065">
  <img width="30%" alt="Screenshot 2024-02-11 at 7 35 36 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/4ef8bc46-0e72-47dc-bd69-59726483386c">
  <img width="30%" alt="Screenshot 2024-02-11 at 7 35 36 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/1d5c58a4-ee18-4d09-be87-52b1fb330ce7">
  

 
- **미션**
  
  친구와 더 친해지고, 한국과도 더 친해질 수 있도록 다양한 미션을 제공합니다. <br>
  친구와 함께 미션을 수행할 수 있고, 마이페이지에서 완료한 미션을 한눈에 확인할 수 있습니다.

  <img width="40%" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/d8bf1689-43aa-4256-b39b-3c4cb85a5f67">
  <img width="40%" alt="Screenshot 2024-02-11 at 7 36 18 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/35088c23-a8ac-4852-80dc-523722dd97e7">
  
<br><br>

## 아키텍쳐
<img width="80%" alt="Screenshot 2024-02-11 at 6 46 37 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/ee0eff4e-9662-450c-83fe-30d363a34ef4">

<br><br>

## ERD
<img width="80%" alt="Screenshot 2024-02-11 at 6 46 37 PM" src="https://github.com/Da-gather/Dagather-Backend/assets/79203421/ba875055-e518-45c3-8202-0ae3dd011ebb">
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
콘텐츠 기반 필터링을 활용하여 사용자와 잘 맞을 것 같은 사용자를 서로에게 추천해줍니다.

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
<br>

2. 사용자의 가입 목적에 따라 가입목적/취미/거리/거주기간의 가중치를 달리하여 최종 유사도를 도출합니다.
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

<br><br>

## 에러 핸들링
Execption의 종류를 세분화하여 HttpsStatus에 맞게 ExceptionHandler를 커스텀하였습니다.

```java
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ApiResponse<Object> handleMethodArgNotValidException(MethodArgumentNotValidException exception,
		HttpServletRequest request) {
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		logInfo(request, HttpStatus.BAD_REQUEST, message);
		return ApiResponse.error(HttpStatus.BAD_REQUEST, message);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ApiResponse<Object> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getMessage());
		return ApiResponse.error(exception.getCode());
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(ForbiddenException.class)
	public ApiResponse<Object> handlerForbiddenException(ForbiddenException exception, HttpServletRequest request) {
		logInfo(request, exception.getCode().getStatus(), exception.getMessage());
		return ApiResponse.error(exception.getCode());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiResponse<Object> unhandledExceptionHandler(Exception exception, HttpServletRequest request) {
		logWarn(request, exception);
		return ApiResponse.error(ErrorCode.SERVER_ERROR);
	}
}
```

<br><br>

## 프로필 리스트 조회 성능 개선
### 문제 상황
프로필 리스트 조회 시 가입 목적과 취미, 거리를 기반으로 추천순 정렬이 필요했습니다.

기존 DB 구조는 가입 목적(Purpose)과 취미(Interest) 테이블이 별도로 존재해, 프로필 리스트를 조회한 후 가입 목적과 취미를 반복문으로 돌며 하나의 리스트로 합치는 방식으로 코드를 작성했습니다.

그 결과 API의 응답 속도가 8초 가량 소요되었습니다.

```java
List<String> myPurposes = new ArrayList<>();
profilePurposeRepository.findAllByProfile(myProfile).forEach(p -> { myPurposes.add(p.getPurpose()); });

List<String> myInterests = new ArrayList<>();
profileInterestRepository.findAllByProfile(myProfile).forEach(i -> { myInterests.add(i.getInterest()); });
```

### 해결 방안
이러한 문제를 해결하기 위해, 다음 두 가지 방안으로 고려해보았습니다.

1. DB I/O를 줄이기 위해 join하는 sql을 직접 짠다.
2. 가입목적과 취미 테이블을 프로필 테이블에 합친다.

가입목적과 취미가 프로필 조회 시 거의 항상 함께 조회된다는 점을 고려해 2번 반정규화를 선택했습니다.

프로필과 가입목적, 취미는 1:N 관계이기 때문에, 별도의 Converter를 구현하여 가입목적과 취미 칼럼에 사용했습니다.

```java
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
	private static final String SPLIT_CHAR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> stringList) {
		return String.join(SPLIT_CHAR, stringList);
	}

	@Override
	public List<String> convertToEntityAttribute(String string) {
		return Arrays.asList(string.split(SPLIT_CHAR));
	}
}
```
```java
@Column(nullable = false)
@Convert(converter = StringListConverter.class)
private List<String> purpose;

@Column(nullable = false)
@Convert(converter = StringListConverter.class)
private List<String> interest;
```

### 결과
프로필 리스트 조회 API의 응답 속도는 약 3초로, 기존 대비 2.6배 향상되었습니다. 

<br><br>

## 폴더 구조
```bash
├── DagatherApplication.java
├── common
│   ├── config
│   │   ├── RestTemplateConfig.java
│   │   └── WebMvcConfig.java
│   ├── exception
│   │   ├── CustomException.java
│   │   ├── DuplicateException.java
│   │   ├── ForbiddenException.java
│   │   ├── NotFoundException.java
│   │   ├── NumberFormatException.java
│   │   └── RestExceptionHandler.java
│   ├── response
│   │   ├── ApiResponse.java
│   │   ├── ErrorCode.java
│   │   └── SuccessCode.java
│   └── util
│       ├── AuthUtil.java
│       └── S3Util.java
└── domain
    ├── friend
    │   ├── controller
    │   │   └── FriendController.java
    │   ├── dto
    │   │   ├── FriendChatroomRequestDto.java
    │   │   ├── FriendChatroomResponseDto.java
    │   │   ├── FriendListResponseDto.java
    │   │   ├── FriendMapper.java
    │   │   ├── FriendRequestDto.java
    │   │   └── FriendResponseDto.java
    │   ├── entity
    │   │   └── Friend.java
    │   ├── repository
    │   │   └── FriendRepository.java
    │   └── service
    │       └── FriendService.java
    ├── mission
    │   ├── controller
    │   │   └── MissionController.java
    │   ├── dto
    │   │   └── MissionSaveRequestDto.java
    │   ├── entity
    │   │   └── Mission.java
    │   ├── repository
    │   │   └── MissionRepository.java
    │   └── service
    │       └── MissionService.java
    ├── mission_complete
    │   ├── controller
    │   │   └── MissionCompleteController.java
    │   ├── dto
    │   │   ├── MissionCompleteCountResponseDto.java
    │   │   ├── MissionCompleteProfileResponseDto.java
    │   │   ├── MissionCompleteResponseDto.java
    │   │   ├── MissionCompleteSaveRequestDto.java
    │   │   ├── MissionCompleteSaveResponseDto.java
    │   │   ├── MissionCompleteUpdateRequestDto.java
    │   │   └── MissionCompleteUpdateResponseDto.java
    │   ├── entity
    │   │   ├── BaseTimeEntity.java
    │   │   └── MissionComplete.java
    │   ├── repository
    │   │   └── MissionCompleteRepository.java
    │   └── service
    │       └── MissionCompleteService.java
    └── profile
        ├── controller
        │   └── ProfileController.java
        ├── dto
        │   ├── ProfileGetListResponseDto.java
        │   ├── ProfileGetResponseDto.java
        │   ├── ProfileImagePostRequestDto.java
        │   ├── ProfileImagePostResponseDto.java
        │   ├── ProfileInterestDto.java
        │   ├── ProfileMapper.java
        │   ├── ProfilePurposeDto.java
        │   ├── ProfileRecommendRequestDto.java
        │   ├── ProfileRecommendRequestItem.java
        │   ├── ProfileRecommendResponseDto.java
        │   ├── ProfileRequestDto.java
        │   └── ProfileResponseDto.java
        ├── entity
        │   ├── Location.java
        │   ├── Profile.java
        │   └── StringListConverter.java
        ├── repository
        │   ├── LocationRepository.java
        │   └── ProfileRepository.java
        └── service
            └── ProfileService.java
```

<br><br>

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

# 미션 - 페어매칭관리 애플리케이션

미션을 함께 할 페어를 관리해주는 애플리케이션 구현

# 구현해야 할 기능 목록

## 파일로부터 백엔드, 프론트엔드 크루명 불러오기

- `src/main/resources/backend-crew.md`로부터 백엔드 크루 목록을 불러온다.
- `src/main/resources/frontend-crew.md`로부터 프론트엔드 크루 목록을 불러온다.

## 기능 선택

```
기능을 선택하세요.
1. 페어 매칭
2. 페어 조회
3. 페어 초기화
Q. 종료
```

- 기능의 종류를 출력하고, 사용자 입력을 받는다.
    - 입력 검증
        - 기능의 종류에 맞는 입력이어야 한다.
        - 올바르지 않은 입력에 대해 에러 메세지 출력 후 재입력을 요구한다.

## 페어 매칭

1. 과정과 미션을 출력하고 매칭하고자 하는 과정, 레벨, 미션을 입력 받는다.

```
#############################################
과정: 백엔드 | 프론트엔드
미션:
  - 레벨1: 자동차경주 | 로또 | 숫자야구게임
  - 레벨2: 장바구니 | 결제 | 지하철노선도
  - 레벨3: 
  - 레벨4: 성능개선 | 배포
  - 레벨5: 
############################################
과정, 레벨, 미션을 선택하세요.
ex) 백엔드, 레벨1, 자동차경주
```

- 입력 검증
    - 존재하는 과정, 레벨, 미션명이어야 한다.
    - 미션명과 레벨이 올바르게 조합되어야 한다.

2. 이미 매칭된 이력이 있으면 재매칭 여부를 사용자로부터 입력 받는다.

```
매칭 정보가 있습니다. 다시 매칭하시겠습니까?
네 | 아니오
```

- 입력 검증
    - 올바르지 않은 입력에 대해 에러 메세지를 출력하고 재입력을 받는다.

3. 페어 매칭을 진행한다.

- 크루들의 이름 목록을 List<String> 형태로 준비한다.
- 크루 목록의 순서를 랜덤으로 섞는다.  `camp.nextstep.edu.missionutils.Randoms`의 shuffle 메서드를 활용해야 한다.
- 랜덤으로 섞인 페어 목록에서 페어 매칭을 할 때 앞에서부터 두명씩 페어로 묶는다.
- 홀수인 경우 마지막 남은 크루는 마지막 페어에 포함시킨다. (3명인 페어 발생)
- 같은 레벨에서 이미 페어로 만난적이 있는 크루끼리 매칭됐다면 다시 매칭을 시작한다. (매칭 실패)
- 3회 시도까지 매칭에 실패하는 경우 에러 메시지를 출력한다.

4. 페어 매칭 결과를 출력한다.

```
페어 매칭 결과입니다.
다비 : 신디
쉐리 : 덴버
제키 : 로드
라라 : 윌터
니콜 : 이브
린다 : 시저
보노 : 제시 : 제키
```

## 페어 조회

1. 과정과 미션을 출력하고 조회하고자 하는 과정, 레벨, 미션을 입력 받는다.

```
#############################################
과정: 백엔드 | 프론트엔드
미션:
  - 레벨1: 자동차경주 | 로또 | 숫자야구게임
  - 레벨2: 장바구니 | 결제 | 지하철노선도
  - 레벨3: 
  - 레벨4: 성능개선 | 배포
  - 레벨5: 
############################################
과정, 레벨, 미션을 선택하세요.
ex) 백엔드, 레벨1, 자동차경주
```

- 입력 검증
    - 페어 매칭과 동일

2. 페어 매칭 조회 결과 출력

```
페어 매칭 결과입니다.
이브 : 윌터
보노 : 제키
신디 : 로드
제시 : 린다
시저 : 라라
니콜 : 다비
리사 : 덴버 : 제키
```

## 초기화 기능

- 페어 매칭 이력을 모두 삭제한다.
- 같은 레벨에서 만났던 이력은 삭제하지 않는다.
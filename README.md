# Repertory
![readmehead](/uploads/a31db15f118b12f020aac31836e48bb4/readmehead.png)
## 🔍 About
### 브레이킹 댄서들의 온라인 레파토리수첩, Repertory입니다! <br>
우리는 프리스타일도 좋아하지만, 레파토리(이하 레파)를 필연적으로 짜야하는 운명이죠. <br>
이 서비스의 기획자인 **bboy Alloy**(안준현)도 그 운명안에 있었습니다. <br>
창작의 과정은 고통스러운 법이기에, 그 과정에서 불만도 많았죠 <br>
아래는 그 불만들입니다.

### 불편한 점

1. 연습실에서 괜찮은 소스를 짜놓고 연결하지 못해서 그대로 잊어버리는 경우가 있다.
2. 수첩이나 메모장에 기록해도, 글만 보면 동작이 생각나지 않을 때가 있다.
3. 머리로 생각했을땐 괜찮은 흐름인데, 실제로 해보면 별로일 때가 많다.
4. 레파를 짜놓고 잊어버리는 경우도 있다.

아마 여러분도 비슷한 고민을 하고 계실겁니다. 그래서 저는 이 불만점들을 어떻게 해결할 좋은 방법이 없을까 생각하다가, 웹상에서 소스를 추출하고, 재배치해서 레파토리를 만들고, 소스와 레파토리를 모아서 관리하는 서비스가 있으면 좋겠다고 생각했고, 그게 저희 서비스인 **Repertory** 입니다. 주요 기능은 아래에서 따로 설명드리고, 이 서비스로 어떤 이점을 얻을 수 있는지 알려드리겠습니다.
### Repertory를 사용하면,,,
1. 레파토리를 영상으로 쉽게 관리할 수 있다.
2. 소스를 잊어버리지 않고 보관할 수 있다.
3. 소스를 태그별로 분류하여 원하는 흐름의 레파토리를 짜기 용이하다
4. 레파토리를 미리 짜고, 느낌을 미리 볼 수 있다.
5. 다른사람의 소스도 내 레파토리에 적용해볼 수 있다.

그동안은 갤러리에 저장하거나, 인스타에 저장하거나 하는 등 어떻게든 다른 수단을 이용해서 소스와 레파를 보관하셨겠죠? 그래서 레파토리 수첩을 사용하지 않을 수 없었을 겁니다. 이 서비스를 이용해보세요!! 비보이 7년차의 경험을 녹여 만들었습니다.
### 미래 비전
- 앱으로 확장
- 소스 공유, 거래기능 추가
- 글로벌 댄서들의 필수 앱

앞으로 더욱 뻗어나갈 우리의 repertory서비스를 지켜봐주세요!!! 


## 💡 Main Feat
#### 영상편집
#### 다음 동작 추천
#### 커뮤니티

## Architecture
![architecture](/uploads/ea92afd9ece33f5f3deb360f13037636/architecture.png)

## ER Diagram
- Community, Member 
<img src = "/uploads/837fa1d43314c90872bbcafe5ec9a091/Community.png" width="49%" height="50%">
<img src = "/uploads/133f6c2618a7ff72002b4f1cd79febdf/Member.png" width="50%" height="50%">

- Dance
<img src = "/uploads/71a0d388960cdca7c4744085eb9ba4a8/Dance.png">


## 🛠 Tech Stack 
|  역할 | 종류 |
| --- | --- |
| Backend Framework | ![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Django](https://img.shields.io/badge/django-%23092E20.svg?style=for-the-badge&logo=django&logoColor=white) |
| Front Framework | ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) ![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white) |
| Database | ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB-%234ea94b.svg?style=for-the-badge&logo=mongodb&logoColor=white) ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white) |
| CI/CD | ![Jenkins](https://img.shields.io/badge/jenkins-%232C5263.svg?style=for-the-badge&logo=jenkins&logoColor=white) ![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)  |
| Version Control | ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitLab](https://img.shields.io/badge/gitlab-%23181717.svg?style=for-the-badge&logo=gitlab&logoColor=white) |
| Communication | ![Mattermost](https://img.shields.io/badge/mattermost-%23D4D4D4.svg?style=for-the-badge&logo=AngelList&logoColor=black) |


## 📦 How to Build


```
npm i
npm run dev
```

## License
Repertory.online is [GNU](https://opensource.org/license/gpl-2-0/) Licensed


## 팀원 정보
|  김세진 | 김형진 | 안준현 | 이성호 | 이주희 | 최세은 |
| --- | --- | --- | --- | --- | --- |
| FE | FE | 팀장, BE | BE | BE | BE |
| `커뮤니티` `프로필` | `영상편집` | `발표` `멤버서비스` | `프로젝트서비스` `댄스서비스` | `CI/CD` `커뮤니티서비스` | `DB설계` `포즈감지서비스` |
| 1 | 2 | 3 | 1 | [@zoohee](https://github.com/zoohee) | 3 |








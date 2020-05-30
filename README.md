# BlockGame
WebアプリケーションフレームワークのSpringを利用して作成したブロック崩しゲーム．
アカウント登録・削除機能，ゲームスコアのランキング機能を実装している．
 
# DEMO
![BlockGame_Demo](https://user-images.githubusercontent.com/65770398/83329730-18b59880-a2c6-11ea-845b-09e582eca615.gif)
 
# Requirement and Installation
- OpenJDK
  - [jdk.java.net](https://openjdk.java.net)からOSに合わせてインストールする.
  - ver.11とver.14で動作確認済み.
  - インストールしたJDKのbinフォルダのパスを環境変数に設定する.
- Spring Tools Suite(STS)
  - Springに特化した統合開発環境 .
  - [Spring Tools4(STS)](https://spring.io/tools)からOSに合わせてインストールする.
  - ダウンロードしたjarファイルが置いてあるディレクトリに移動し，`java -jar ファイル名`で展開する．展開後に作成されたフォルダ内のexeファイルを実行するとSTSが起動する.
  
- STSの設定（Eclipce版の場合）
  - Help > Eclipce Marketplace WizardからビルドツールとしてGradleのプラグインをインストールする.
  - Window > PreferenceタブのJava > Installes JREsでインストールしたJREを指定する.

# Usage
1. 本プロジェクトをダウンロードし，File > Importとしてインポート．
2. プロジェクトを実行（Run As Spring Boot App）.
3. ブラウザからlocalhost:8080/user/indexにアクセス.
 
# Note
インメモリデータベースであるH2データベースを使用してアカウント情報やゲームスコアの管理を行っているため，データベースはアプリ起動時に初期化される．
 
# Author
* Itsuki Hashimoto
* Naoto Yoshimura
* Chihiro Tomita
* Junpei Matsue
* Takuya Ohara
* Ryusei Nagasawa

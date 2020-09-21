# Gradleマルチプロジェクトのサンプル２

## 方針
* Gradleのマルチプロジェクトを利用し、設定や処理の共通化をする
* SVNで個々のサブプロジェクトをチェックアウトし、開発ができる
  * ↑に加え、masterプロジェクトのチェックアウトは必須
* 最新の公開Jarを共有・参照して開発ができる
  * 個々のサブプロジェクトで公開Jarを作成し、Mavenリポジトリにアップロードできる
  * 常に最新の公開Jarを参照できるようにする
* 個々のサブプロジェクトでビルド・デプロイができるようにする

## 用語集
* 公開Jar：社内MavenリポジトリにアップロードするJar
* 公開Jar（Web）：社内Mavenリポジトリにアップロードする、Webリソースを含むJar
* 基盤Jar：Mevenセントラルリポジトリから取得するJar

## コードライン運用
* ビルドしてJarを作成し、その結果をステージングタグに登録する
* 指定のステージングタグから、開発者指定の環境に沿ったリソースを集約し、開発者指定の環境にデプロイする

### UT
1. trunkからUTブランチを作成
1. UTが完了したらUTブランチにコミット

### CT
1. UTブランチからステージングブランチを作成する
1. 改修したリソースは、UTブランチからステージングブランチにマージ
1. ステージングブランチを基にJenkinsがビルドし、ステージングタグを作成
1. 開発者がステージングタグ、環境、リソースを指定し、Jenkinsがデプロイ

### ST
1. 開発者がステージングタグ、環境、リソースを指定し、Jenkinsがデプロイ

### 本番
1. 開発者がステージングタグ、環境、リソースを指定し、Jenkinsがデプロイ

## プロジェクト構成
### サブプロジェクトの種類とリリース対象のリソース
* 公開Jar（Web）は、業務Webアプリケーションに含むので、サーバにデプロイはしない
* Webプロジェクトにシェルは不要

#### 基盤ソフトウェア
* ドメイン：公開Jar, シェル, 外部設定ファイル
* Web：外部設定ファイル
* バッチ：公開Jar, シェル, 外部設定ファイル

#### 業務共通
* ドメイン：公開Jar, シェル, 外部設定ファイル
* Web：外部設定ファイル
* バッチ：公開Jar, シェル, 外部設定ファイル

#### 業務アプリケーション
* Web：War, 外部設定ファイル
* バッチ：業務Jar, シェル, 外部設定ファイル

※ Warの中に含まれるため、直接サーバにデプロイはしない

### アーカイブの作成単位
* 公開Jarはサブプロジェクト単位
* Warはサブプロジェクト単位
* 業務バッチJarはグループID.batch直下のパッケージ単位

## Webリソースを含む公開Jar
* コーディング時は、warでサーバにデプロイできるようにする
* 公開Jar作成時は、META-INF/resourcesにWebリソースを含める

## クラスパス
* 業務Jar, 公開Jarは同一のディレクトリ
* 基盤Jarは上記のサブディレクトリ

# 商品管理アプリ（product_app）

**本アプリは授業の一環として作成したものです。**  
講義・演習で Spring Boot と Web アプリケーションの基礎を学ぶために開発した教材用のサンプルアプリケーションです。

---

## 概要

ログイン機能付きの商品管理 Web アプリケーションです。商品の登録・一覧表示・編集ができ、管理者ロールでログインした場合は削除も行えます。

- **技術スタック**: Java 21, Spring Boot 4.x, Spring Security, Spring Data JPA, Thymeleaf, PostgreSQL（Supabase）
- **役割**: 一般ユーザー（閲覧・登録・編集）、管理者（上記に加えて削除可能）

---

## 主な機能

| 機能           | 説明                                   |
|----------------|----------------------------------------|
| ログイン/ログアウト | フォーム認証（Spring Security）        |
| 商品登録       | 商品名・価格を入力して登録              |
| 商品一覧       | 登録済み商品の一覧表示                  |
| 商品編集       | 既存商品の名前・価格の更新              |
| 商品削除       | 管理者のみ実行可能                      |

---

## 動作環境

- Java 21
- Maven 3.6+
- PostgreSQL（本プロジェクトでは Supabase を利用想定）

---

## セットアップ

### 1. リポジトリの取得

```bash
git clone <リポジトリURL>
cd product_app
```

### 2. データベースの準備

PostgreSQL のデータベースを用意し、次のテーブルを作成してください。

- **users** テーブル: `id`, `username`, `password_hash`, `role`  
  - 管理者ユーザーは `role = 'ADMIN'` に設定
- **product** テーブル（または `Product` エンティティに合わせたテーブル名）: 商品の ID, 名前, 価格など

（授業で配布された SQL やマイグレーションがある場合はそちらに従ってください。）

### 3. 環境変数（任意）

Supabase 等で DB を利用する場合、必要に応じて以下を設定します。

- `SUPABASE_JDBC_URL` … JDBC URL
- `SUPABASE_DB_USER` … DB ユーザー名
- `SUPABASE_DB_PASSWORD` … DB パスワード

未設定の場合は `src/main/resources/application.properties` のデフォルト値が使われます。

### 4. ビルド・起動

```bash
./mvnw spring-boot:run
```

ブラウザで `http://localhost:8080` にアクセスし、ログイン画面が表示されれば起動完了です。

---

## プロジェクト構成（抜粋）

```
src/main/java/com/example/product_app/
├── ProductAppApplication.java    # エントリポイント
├── config/                       # DataSeeder 等
├── controller/                   # Home, Product, Auth, Dashboard
├── entity/                       # User, Product
├── form/                         # ProductForm
├── repository/                   # JPA リポジトリ
├── security/                     # SecurityConfig
└── service/                      # ProductService, UserDetailsServiceImpl
src/main/resources/
├── application.properties
├── static/css/style.css
└── templates/                    # Thymeleaf テンプレート（index, login, edit 等）
```

---

## 注意事項

- 本アプリは**授業・演習用**であり、その目的の範囲で利用することを想定しています。
- 認証情報や DB 接続情報は環境変数や設定ファイルで管理し、リポジトリに機密情報をコミットしないよう注意してください。

---

## ライセンス・利用について

授業で作成した教材用サンプルのため、利用・改変は授業の範囲内で行ってください。不明点は担当教員に確認してください。

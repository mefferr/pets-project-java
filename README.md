### Backend:

1. **Logowanie:**
   - Wykorzystuje Spring Security i JSON Web Token (JWT).
   - Po udanym logowaniu, token zapisuje się w plikach cookie.

2. **Baza Danych:**
   - Korzysta z bazy danych H2.

3. **Testy:**
   - Wprowadzone testy end-to-end.

### Frontend:

1. **Technologie:**
   - Frontend zbudowany na React.
   - Do stylizacji wykorzystuje Tailwind CSS z dodatkiem DaisyUI.

## Jak Uruchomić:

### Backend:

Przejdź do katalogu projektu i wykonaj poniższą komendę:

```bash
./gradlew.bat bootRun
```

### Frontend:

Przejdź do folderu klienta (```cd client```) i wykonaj poniższe polecenia:

```bash
npm install
npm run dev
```

export default function () {


    return <form onSubmit={async (e) => {
        e.preventDefault();
        const email = document.querySelector("[name=email]");
        const password = document.querySelector("[name=password]");
        const body = JSON.stringify({email: email.value, password: password.value});

        const res = await fetch("http://localhost:8080/api/users/login", {
            body, headers: {"Content-Type": "application/json"}, method: "POST", credentials: 'include'
        });

        if (res.status !== 200) {
            return document.querySelector("#err").textContent = "Nie udalo sie zalogowac";
        }

        return window.location.replace("/")
    }} className="artboard phone-1 bg-gray-200 flex flex-col p-5 gap-10">
        <p className={"text-xl"}>Logowanie (t@t.com / test)</p>
        <p className={"text-red-500 text-xl"} id={"err"}></p>
        <label>
            Email
            <input type="text" placeholder="Email" name={"email"} className="input input-bordered w-full max-w-xs"/>
        </label>
        <label>
            Haslo
            <input type="password" placeholder="Haslo" name={"password"}
                   className="input input-bordered w-full max-w-xs"/>
        </label>
        <button type={"submit"} className={"btn btn-primary"}>
            Zaloguj sie
        </button>
    </form>
}
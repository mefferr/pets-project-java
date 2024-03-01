export default class PetController {
    static async getPets() {
        const res = await (await fetch("http://localhost:8080/api/pets/", {credentials: 'include'}));

        console.log(res.status)
        if (res.status === 403) {
            return window.location.replace("/login");
        }

        return await res.json();
    }

    static async addPet(name, birth, sex) {
        const res = await (await fetch("http://localhost:8080/api/pets/", {
            credentials: 'include',
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({name, birth, sex})
        }));

        if (res.status === 403) {
            return window.location.replace("/login");
        }

        return await res.json();
    }

    static async editPet(id, name, birth, sex) {
        const res = await (await fetch(`http://localhost:8080/api/pets/${id}/`, {
            credentials: 'include',
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({name, birth, sex})
        }));

        if (res.status === 403) {
            return window.location.replace("/login");
        }

        return await res.json();
    }
}
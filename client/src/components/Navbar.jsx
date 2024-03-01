import PetController from "../PetController.js";

export default function Navbar() {
    return <div className="navbar bg-base-100">
        <div className="flex-1">
            <a className="btn btn-ghost text-xl">Moje zwierzęta</a>
        </div>
        <div className="flex-none">
            <button onClick={() => document.getElementById('my_modal_1').showModal()}
                    className={"btn btn-primary"}>Dodaj
            </button>
        </div>

        <dialog id="my_modal_1" className="modal">
            <div className="modal-box">
                <h3 className="font-bold text-lg">Dodaj zwierzaka!</h3>
                <form className={"flex flex-col gap-5"} onSubmit={(event) => {
                    event.preventDefault();

                    const name = document.querySelector("#name").value;
                    const birth = document.querySelector("#birth").valueAsDate;
                    const sex = document.querySelector("#sex").value;

                    PetController.addPet(name, birth, sex).then(() => window.location.reload());
                }}>
                    <label className="input input-bordered flex items-center gap-2">
                        Imie
                        <input id={"name"} type="text" className="grow" placeholder="Daisy"/>
                    </label>
                    <label className="input input-bordered flex items-center gap-2">
                        Data urodzenia
                        <input id={"birth"} type="date" className="grow" placeholder="daisy@site.com"/>
                    </label>
                    <select id={"sex"} className="select select-bordered w-full">
                        <option disabled selected>Plec</option>
                        <option value={"MALE"}>Samiec</option>
                        <option value={"FEMALE"}>Samica</option>
                    </select>
                    <button type={"submit"} className={"btn btn-info"}>Dodaj</button>
                </form>
                <div className="modal-action">
                    <form method="dialog">
                        <button className="btn">Anuluj</button>
                    </form>
                </div>
            </div>
        </dialog>
    </div>
}
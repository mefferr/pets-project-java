import {useEffect, useState} from "react";
import PetController from "../PetController.js";
import Navbar from "../components/Navbar.jsx";
import moment from "moment";

export default function () {
    const [pets, setPets] = useState([]);

    useEffect(() => {
        PetController.getPets().then(setPets);
    }, []);


    return (<>
        <Navbar/>
        <div className="divider"></div>

        <div className="overflow-x-auto p-10">
            <table className="table table-lg">
                <thead>
                <tr>
                    <th></th>
                    <th>Imię</th>
                    <th>Data urodzenia</th>
                    <th>Płeć</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                {pets.map((pet, index) => <tr className={"hover hover:hover"} key={index}>
                    <td>{index + 1}</td>
                    <td>{pet.name}</td>
                    <td>{new Date(pet.birth).toLocaleDateString()}</td>
                    <td>{pet.sex === "MALE" ? "Samiec" : "Samica"}</td>
                    <td className={"hover cursor-pointer"} onClick={() => {
                        fetch(`http://localhost:8080/api/pets/${pet.id}/`, {method: "DELETE", credentials: 'include'}).then(() => window.location.reload())
                    }}>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                             stroke="currentColor" className="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                  d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"/>
                        </svg>
                    </td>
                    <td onClick={() => document.getElementById('my_modal_2').showModal()} className={"hover cursor-pointer"}>
                        <dialog id="my_modal_2" className="modal">
                            <div className="modal-box">
                                <h3 className="font-bold text-lg">Edytuj zwierzaka!</h3>
                                <form className={"flex flex-col gap-5"} >
                                    <label className="input input-bordered flex items-center gap-2">
                                        Imie
                                        <input id={"edit-name"} type="text" className="grow" defaultValue={pet.name} placeholder="Daisy"/>
                                    </label>
                                    <label className="input input-bordered flex items-center gap-2">
                                        Data urodzenia
                                        <input type="date" id={"edit-birth"} defaultValue={moment(new Date(pet.birth)).format('YYYY-MM-DD')}/>
                                    </label>
                                    <select id={"edit-sex"} defaultValue={pet.sex} className="select select-bordered w-full">
                                        <option disabled selected>Plec</option>
                                        <option value={"MALE"}>Samiec</option>
                                        <option value={"FEMALE"}>Samica</option>
                                    </select>
                                    <button type={"button"} onClick={() => {
                                        console.log("gowno")
                                        const name = document.querySelector("#edit-name").value;
                                        const birth = document.querySelector("#edit-birth").valueAsDate;
                                        const sex = document.querySelector("#edit-sex").value;

                                        PetController.editPet(pet.id, name, birth, sex).then(() => window.location.reload()).catch(console.error)
                                    }} className={"btn btn-info"}>Edytuj</button>
                                </form>
                                <div className="modal-action">
                                    <form method="dialog">
                                        <button className="btn">Close</button>
                                    </form>
                                </div>
                            </div>
                        </dialog>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                             stroke="currentColor" className="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                  d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125"/>
                        </svg>
                    </td>
                </tr>)}
                </tbody>
            </table>
        </div>
    </>)
}
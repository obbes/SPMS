"use strict"

checkLogin().then(populatePitches).then(makePitch).then(insertFileUpload);
//makePitch();
let isAuthor = false;

function populatePitches() {
    let pitches = loggedUser.pitches;
    let pitchSection = document.getElementById('pitchSection');

    if (pitches.length > 0) {
        let table = document.createElement('table');
        table.innerHTML = `
            <tr>
                <th>ID</th>
                <th>Author ID</th>
                <th>Story Title</th>
                <th>Story Type</th>
                <th>Genre</th>
                <th>Description</th>
                <th>Status</th>
                <th>Priority</th>
                <th>Stage</th>
                <th>Finish Date</th>

                <th> ReSubmit? </th>
            </tr>
        `;

        for (let pitch of pitches) {

            let tr = document.createElement('tr');
            tr.innerHTML = `
                <td id ="${pitch.id}pitchId">${pitch.id}</td>
                <td id ="${pitch.id}pitchAuth">${pitch.author}</td>
                <td id ="${pitch.id}pitchTitle">${pitch.story_title}</td>
                <td id ="${pitch.id}pitchType">${pitch.story_type.name}</td>
                <td id ="${pitch.id}pitchGenre">${pitch.genre.name}</td>
                <td id ="${pitch.id}pitchDesc">${pitch.description}</td>
                <td id ="${pitch.id}pitchStatus">${pitch.status.name}</td>
                <td id ="${pitch.id}pitchPriority">${pitch.priority.name}</td>
                <td id ="${pitch.id}pitchStage">${pitch.stage.name}</td>
                <td id ="${pitch.id}pitchFinish">${pitch.finish_date}</td>

            `;
            if(pitch.status.id >= 3){
                let td1 = document.createElement('td');
                td1.innerHTML =`
                <button id="reSubPitch${pitch.id}" type="button" value="${pitch.id}" 
                onclick="reSubPitch(${pitch.id})">Resubmit Pitch? 
                </button>
                `;
                tr.appendChild(td1);
            }
            if(pitch.stage.id === 4){
                let td = document.createElement('td');
                td.innerHTML = `
                <button id="draft${pitch.id}" onclick = "draftSubmit(${pitch.id})" type="button">
                    Submitted Draft?
                </button>
                `;
                tr.appendChild(td);
            }
            table.appendChild(tr);


        } //end for
        pitchSection.appendChild(table);
    } else {
        pitchSection.innerHTML = 'Authors may make pitches';

    }//else of if
    let titles = loggedUser.title;
    if (titles.length > 0) {
        for (let title of titles) {
            if (title.name === 'author') {
                isAuthor = true;
                break;
            }
        }
    }
} //end poppitch 

function makePitch() {

    if (isAuthor) {
        let makeSection = document.getElementById('makeSection');
        makeSection.innerHTML = "New Pitch Form";

        let pitchform = document.createElement('form');
        pitchform.id = 'form';

        pitchform.innerHTML = `
        <input type='text' id='story_title' placeholder= 'Story Title'><br>
        <label for="story_type">Choose story length:</label>

        <select name="story_type" id="story_type">
        <option value="1">Novel</option>
        <option value="2">Novella</option>
        <option value="3">Short Story</option>
        <option value="4">Article</option>
        </select><br>

        <label for="genre">Choose Genre:</label>

        <select name="genre" id="genre">
        <option value="1">Fantasy</option>
        <option value="2">Sci-fi</option>
        <option value="3">Fiction</option>
        <option value="4">History</option>
        <option value="5">Cooking</option>
        <option value="6">Biography</option>
        <option value="7">Sports</option>
        <option value="8">Kids</option>
        </select><br>
        <br>
        <textarea rows="4" cols="50" id="description" form="newPitch">
        Description of Story...</textarea>
        <br>
        <label for="fdate">Finish date:</label>

        <input type="date" id="fdate" name="finish_date"
               value="2020-12-01"
               min="2020-12-01" max="2021-12-31">
        <br>
        <button id="submitChanges" onclick="submitChanges" type="button">
               Submit Changes
        </button>
    `;
        //document.getElementById("form").appendChild(document.createElement("br"));
        // insertFileUpload();



        makeSection.appendChild(pitchform);
        let pitchButton = document.getElementById('submitChanges');
        pitchButton.addEventListener("click", submitChanges);

    }
}

function insertFileUpload() {
    let label = document.createElement("label");
    label.setAttribute("for", "additionalFile");
    let labelText = document.createTextNode("Please upload Resources: ");
    label.appendChild(labelText);
    document.getElementById("form").appendChild(label);
    let file = document.createElement("input");
    file.type = "file";
    file.id = "additionalFile";
    file.name = "additionalFile";
    file.multiple = true;
    file.ondrop = "drop(event)";
    file.ondragover = "allowDrop(event)";
    document.getElementById("form").appendChild(file);
    let clearFile = document.createElement("button");
    clearFile.type = "button";
    clearFile.id = "clearFile";
    clearFile.name = "clearFile";
    clearFile.hidden = true;
    let clearText = document.createTextNode("Clear Files");
    clearFile.appendChild(clearText);
    clearFile.onclick = clearFiles;
    document.getElementById("form").appendChild(clearFile);
    file.addEventListener("change", handleFiles, false);
    let fileSection = document.createElement("section");
    fileSection.id = "fileSection";
    document.getElementById("form").appendChild(fileSection);
}


function handleFiles(e) {
    if (!e.target.files) return;
    
    let files = e.target.files;
    let fileSection = document.getElementById("fileSection");
    fileSection.innerHTML = '';
    for (let i = 0; i < files.length; i++) {
        let file = files[i];
        fileSection.innerHTML += file.name + "<br/>";
    }
    document.getElementById("clearFile").hidden = false;
}

function clearFiles() {

    document.getElementById("additionalFile").value = null;
    document.getElementById("additionalFile").files = null;
    let fileSection = document.getElementById("fileSection");
    fileSection.innerHTML = '';
    document.getElementById("clearFile").hidden = true;
}

async function uploadFiles() {
    let url = baseUrl + "/pitch/file";
    let data = new FormData();
    let files = document.getElementById("additionalFile").files;
    let len = files.length;
    for (let i = 0; i < len; i++) {
        data.append("files[]", files[i], files[i].name);
        console.log(files[i].name);
    }
}


async function submitChanges() {

    let pitches = loggedUser.pitches;
    let total = 0;
    let statweight = 0;
    let priorityChecker = 0;
    let pitchStoryType = document.getElementById('story_type');
    let pitchweight = 0;
    if (pitchStoryType.options[pitchStoryType.selectedIndex].text == 'Novel') {
        pitchweight = 50;
    } else if (pitchStoryType.options[pitchStoryType.selectedIndex].text == 'Novella') {
        pitchweight = 25;
    } else if (pitchStoryType.options[pitchStoryType.selectedIndex].text == 'Short Story') {
        pitchweight = 20;
    } else if (pitchStoryType.options[pitchStoryType.selectedIndex].text == 'Article') {
        pitchweight = 10;
    } else {
        alert("Code Monkey get a job!")
    }
    for (let pitch of pitches) {
        if (pitch.status.id < 3) {
            total += pitch.story_type.points;
        }
    }
    if ((total + pitchweight) >= 100) {
        statweight = 4;
        priorityChecker = 3;
    } else {
        statweight = 1;
        priorityChecker = 1;
    }
    let files = [];
    for (let file of document.getElementById("additionalFile").files) {
        files.push(file.name);
    }
    let data = {
        author: loggedUser.id,
        story_title: document.getElementById('story_title').value,
        story_type: {
            id: document.getElementById('story_type').value,
        },
        genre: {
            id: document.getElementById('genre').value
        },
        description: document.getElementById('description').value,
        status: {
            id: statweight
        },
        priority: {
            id: priorityChecker
        },
        stage: {
            id: 1
        },
        finish_date: document.getElementById('fdate').value
        
        /* add files to the pitch, file service not up yet
        ,
        files: files
        */
    };
    let url = baseUrl + '/pitch';

    let response = await fetch(url, { method: 'POST', body: JSON.stringify(data) });
    if (response.status >= 200 && response.status < 300) {
        alert('Pitch sent successfully.');
        document.location.reload();
    } else if (response.status >= 400 && response.status < 500) {
        alert('400 response oh no')
    } else if (response.status === null) {
        alert('there was no response')
    } else {
        alert('Something went wrong.');
    }

}
//Been told its bad practice to actually delete things 
// async function deletePitch(number) {
//     let url = baseUrl + '/users/pitches/' + number;
//     let data = {
//         author: document.getElementById(`${number}pitchId`).value,
//     };


//     let response = await fetch(url, { method: 'DELETE', body: JSON.stringify(data) });
//     if (response.status >= 200 && response.status < 300) {

//         document.location.reload();
//     }
// }


async function draftSubmit(number){
    alert("Sending draft out to be proofread!")
    let data = number;
    let url = baseUrl + '/stage/' + number;
    let response = await fetch(url, {method: 'PUT', body:JSON.stringify(data)});
    if(response.status >= 200 && response.status < 300){
        alert("Congrats on a successful pitch!")
        document.location.reload();
    }else{
        alert("Something may have happened and the accept was not processed");
    }
}

async function reSubPitch(number) {
    //still doesn't work, can't get value out of the tag. keeps return undefined for pType, pType.value, and pType.text
    let total = 0;
    let pitches = loggedUser.pitches;
    let currPitch;
    for (let pitch of pitches) {
        if (pitch.status.id < 3) {
            total += pitch.story_type.points;
        }
        if (pitch.id == number) {
            currPitch = pitch;
        }
    }
    let pitchPoints = 0;
    let setPitchStage = 0;
    let pType = document.getElementById(`${number}pitchType`);
    if (pType.innerHTML == 'novel') {
        pitchPoints = 50;
        setPitchStage ={
            id: 1,
            stage_name: 'assistant approval'
        };
    } else if (pType.innerHTML == 'novelle') {
        pitchPoints = 25;
        setPitchStage ={
            id: 2,
            stage_name: 'general approval'
        };
    } else if (pType.innerHTML == 'short story') {
        pitchPoints = 20;
        setPitchStage ={
            id: 2,
            stage_name: 'general approval'
        };
    } else if (pType.innerHTML == 'article') {
        pitchPoints = 10;
        setPitchStage ={
            id: 3,
            stage_name: 'senior approval'
        };
    } else {
        alert('This pitch has no points and thats no good');
    }

    currPitch = {
        "id": currPitch.id, 
        "author": loggedUser.id,
         "story_title": currPitch.story_title, 
            "story_type": {
            "id": currPitch.story_type.id,
            "name": currPitch.story_type.name,
            "points": currPitch.story_type.points,
            "approval": currPitch.story_type.approval 
        },
            "genre": {
            "id": currPitch.genre.id,
            "name": currPitch.genre.name
            },
        "description": currPitch.description,
        "status": {
            "id": 1,
            "name": "pending"
        },
        "priority": {
            "id": 1,
            "name": 'normal'
        },
        "stage": {
            "id": setPitchStage.id,
            "name": setPitchStage.stage_name
        },
        "finish_date": currPitch.finish_date 
    }
    if (total + pitchPoints <= 100) {
        let url = baseUrl + '/pitch/'+number;
        let response = await fetch(url, { method: 'PUT', body: JSON.stringify(currPitch)});
        if(response.status >= 200 && response.status < 300){
            alert("resubmission successful!")
            document.location.reload();
        }
    }else{
        alert("That pitch is worth too many points to submit, try again later.")
    }
}

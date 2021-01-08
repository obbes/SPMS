let addCatMenuOpen = false;
setup();

function setup() {
        checkLogin().then(() => {
            if (loggedUser.title.title_name === 'author') authorSetup();
            if (loggedUser.title.title_name === 'assistant' ||
                loggedUser.title.title_name === 'general' ||
                loggedUser.title.title_name === 'senior') editorSetup();
        });
}

async function getPitches() {
    let url = baseUrl + '/users';
    let response = await fetch(url);
    if (response.status === 200) {
        let pitches = await response.json();
        populatePitches(pitches);
    }
}

function populatePitches(pitches) {
    let pitchSection = document.getElementById('pitchSection');
    pitchSection.innerHTML = '';

    if (pitches.length > 0) {
        let table = document.createElement('table');
        table.id = 'pitchTable';

        table.innerHTML = `
        <tr>
            <th>ID</th>
            <th>Story Title</th>
            <th>Finish Date</th>
            <th>Story Type</th>
            <th>Genre</th>
            <th>Description</th>
            <th>Status</th>
            <th>Priority</th>
            <th>Stage</th>
        </tr>
        `;

        for (let pitch of pitches) {
            let tr = document.createElement('tr');
            tr.innerHTML = `
            <td>${pitch.id}</td>
            <td>${pitch.story_title}</td>
            <td>${pitch.finish_date}</td>
            <td>${pitch.storytype.storytype_name}</td>
            <td>${pitch.genre.genre_name}</td>
            <td>${pitch.description}</td>
            <td>${pitch.status.status_name}</td>
            <td>${pitch.pitch_priority.priority_name}</td>
            <td>${pitch.pitch_stage.stage_name}</td>
            `;
            let td = document.createElement('td');
            let ul = document.createElement('ul');

            td.appendChild(ul);
            tr.appendChild(td);

            let acceptBtn = document.createElement('button');
            acceptBtn.type = 'button';
            acceptBtn.id = pitch.story_title + " _ " + pitch.id;
            acceptBtn.textContent = 'Accept';
            acceptBtn.disabled = !loggedUser;

            let rejectBtn = document.createElement('button');
            rejectBtn.type = 'button';
            rejectBtn.id = pitch.story_title + " _ " + pitch.id;
            rejectBtn.textContent = 'reject';
            rejectBtn.disabled = !loggedUser;

            let requestBtn = document.createElement('button');
            requestBtn.type = 'button';
            requestBtn.id = pitch.story_title + " _ " + pitch.id;
            requestBtn.textContent = 'request more information';
            requestBtn.disabled = !loggedUser;
           
            let btnTda = document.createElement('td');
            btnTda.appendChild(acceptBtn);
            let btnTdr = document.createElement('td');
            btnTdr.appendChild(rejectBtn);
            let btnTdrq = document.createElement('td');
            btnTdrq.appendChild(requestBtn);
            tr.appendChild(btnTd);
            table.appendChild(tr);
            
            acceptBtn.addEventListener('click', acceptPitch);
            rejectBtn.addEventListener('click', rejectPitch);
            requestBtn.addEventListener('click', requestInfo);
        }

        pitchSection.appendChild(table);
    } else {
        pitchSection.innerHTML = 'This area is empty on purpose';
    }
}

async function adoptCat() {
    let btnId = event.target.id;
    let index = btnId.indexOf('_'); // find underscore (see line 46)
    let id = btnId.slice(index+1); // get text after underscore
    let name = btnId.replace('_', ''); // remove underscore
    if (confirm('You want to adopt ' + name + '?')) {
        let url = baseUrl + '/cats/adopt/' + id;
        let response = await fetch(url, {method:'PUT'});
        switch (response.status) {
            case 200:
                alert('You adopted ' + name + '!');
                break;
            case 409:
                alert('That cat doesn\'t seem to exist...');
                break;
            case 401:
                alert('Hold on, you\'re not logged in!');
                break;
            default:
                alert('Something went wrong.');
                break;
        }
    }
}

function employeeSetup() {
    let employeeSpan = document.getElementById('emp');
    employeeSpan.removeAttribute('hidden');
    // add cat
    if (!(document.getElementById('addCatBtn'))) {
        let addCatBtn = document.createElement('button');
        addCatBtn.type = 'button';
        addCatBtn.textContent = 'Add Cat';
        addCatBtn.id = 'addCatBtn';
        addCatBtn.onclick = addCatMenu;
        employeeSpan.appendChild(addCatBtn);
    }

    // edit cat
    let catsTable = document.getElementById('catTable');
    for (let tr of catsTable.childNodes) {
        if (tr.nodeName === 'TR') {
            let td = document.createElement('td');
            if (tr != catsTable.childNodes.item(0)) {
                let editBtn = document.createElement('button');
                editBtn.id = 'edit_' + tr.childNodes.item(1).textContent;
                editBtn.type = 'button';
                editBtn.textContent = 'Edit';
                editBtn.onclick = editCat;
                td.appendChild(editBtn);
            }
            tr.appendChild(td);
        }
    }
}

function addCatMenu() {
    let employeeSpan = document.getElementById('emp');
    addCatMenuOpen = !addCatMenuOpen;
    console.log('add cat menu open? ' + addCatMenuOpen);

    if (addCatMenuOpen) {
        employeeSpan.innerHTML += `<form id="add-cat-form">
        <label for="name">Name:</label>
        <input type="text" id="name" placeholder="Name" required />
        
        <label for="age">Age:</label>
        <input type="number" id="age" placeholder="age" required />
        
        <label for="breed">Breed:</label>
        <select id="breed" required>

        </select>

        <button type="button" onclick="addCat()" id="submit-add-cat-form" >Submit</button>
        </form>
        `;
        populateBreeds();
        let submitAddBtn = document.getElementById('submit-add-cat-form');
        submitAddBtn.onclick = addCat;
    } else {
        employeeSpan.removeChild(document.getElementById('add-cat-form'));
    }

    let addCatBtn = document.getElementById('addCatBtn');
    addCatBtn.onclick = addCatMenu;
}

function editCat() {
    let editBtn = event.target;
    let editId = event.target.id;
    let editTd = editBtn.parentElement;
    let editTr = editTd.parentElement;

    let nodes = editTr.childNodes;

    editTr.innerHTML = `
        <td>${nodes.item(1).innerHTML}</td>
        <td><input id = "eCName" type = "text" value = ${nodes.item(3).innerHTML}></td>
        <td><input id = "eCAge" type = "text" value = ${nodes.item(5).innerHTML}></td>
        <td>${nodes.item(7).innerHTML}</td>
        <td>${nodes.item(9).innerHTML}</td>
        <td><button disabled = 'true'>Adopt</button>
        <button id = ${editId}>Save</button></td>
        `;
    //<input id = "eCBreed" type = "text" value = ${nodes[3].innerHTML}>
    editBtn = document.getElementById(editId);
    editBtn.addEventListener('click', saveCat);

}

async function saveCat()
{
    let btnId = event.target.id;
    let index = btnId.indexOf('_');
    let id = btnId.slice(index+1); // get text after underscore

    let url = baseUrl + '/cats/' + id;

    let response = await fetch(url);

    let cat = await response.json();

    cat.name = document.getElementById('eCName').value;
    cat.age = document.getElementById('eCAge').value;

    let newResponse = await fetch(url,{method:'PUT',body:JSON.stringify(cat)});
    if (newResponse.status === 200) {
        alert('Updated successfully.');
    } else {
        alert('Something went wrong.');
    }
    
    setup();
}

async function populateBreeds() {
    let breedDropDown = document.getElementById('breed');
    let url = baseUrl + '/breeds';
    let response = await fetch(url);
    if (response.status === 200) {
        let breeds = await response.json();
        for (let breed of breeds) {
            let breedOption = document.createElement('option');
            breedOption.value = breed.id;
            breedOption.textContent = breed.name;
            breedDropDown.appendChild(breedOption);
        }
    } else {
        alert('Something went wrong.');
        addCatMenuOpen = true;
        addCatMenu();
    }
}

async function addCat() {
    let cat = {};
    cat.id = 0;
    cat.name = document.getElementById('name').value;
    cat.age = document.getElementById('age').value;
    cat.status = {};
    cat.status.id = 0;
    cat.status.name = '';
    cat.breed = {};
    cat.breed.id = document.getElementById('breed').value;

    let url = baseUrl + '/cats';
    let response = await fetch(url, {method:'POST', body:JSON.stringify(cat)});
    if (response.status === 201) {
        alert('Added cat successfully.');
    } else {
        alert('Something went wrong.');
    }
    addCatMenu();
    setup();
}
let baseUrl = 'http://localhost:8080';
let nav = document.getElementById('navBar');
let loggedUser = null;
checkLogin();
setNav();
getCommittees();
function setNav() {
    nav.innerHTML = `
            <a href="index.html"><strong>Story Pitch System</strong></a>`;
    if (!loggedUser) {
        nav.innerHTML += `
            <form>
                <label for="user">Username: </label>
                <input id="user" name="user" type="text" />
                <label for="pass"> Password: </label>
                <input id="pass" name="pass" type="password" />
                <button type="button" id="loginBtn">Log In</button>
            </form>
        `;
    } else{
        nav.innerHTML += `
            <a href="currPitches.html">Author Pitches</a>
            <a href="currCommittees.html">Editor Committees</a>
            <a href="currRequests.html">My Info Requests</a>
            
            <span>
                <a href="profile.html">Welcome ${loggedUser.username}&nbsp;</a>
                <button type="button" id="loginBtn">Log Out</button>
            </span>
        `;
    }


    let loginBtn = document.getElementById('loginBtn');
    if (loggedUser) loginBtn.onclick = logout;
    else loginBtn.onclick = login;
}

async function login() {
    let url = baseUrl + '/users?';
    url += 'user=' + document.getElementById('user').value + '&';
    url += 'pass=' + document.getElementById('pass').value;
    let response = await fetch(url, {method: 'PUT'});
    
    switch (response.status) {
        case 200: // successful
            loggedUser = await response.json();
            setNav();
            break;
        case 400: // incorrect password
            alert('Incorrect password, try again.');
            document.getElementById('pass').value = '';
            break;
        case 404: // user not found
            alert('That user does not exist.');
            document.getElementById('user').value = '';
            document.getElementById('pass').value = '';
            break;
        default: // other error
            alert('Something went wrong.');
            break;
    }
}

async function logout() {
    let url = baseUrl + '/users';
    let response = await fetch(url, {method:'DELETE'});

    if (response.status != 200) alert('Something went wrong.');
    loggedUser = null;
    setNav();
    location.replace(baseUrl);
}
var userAuthor = 0;
var userAssistant = 0;
var userGeneral = 0;
var userSenior = 0;
async function checkLogin() {
    let url = baseUrl + '/users';
    let response = await fetch(url);
    if (response.status === 200) loggedUser = await response.json();
    setNav();
    for(let title of loggedUser.title){
        if(title.name === 'author'){
            userAuthor = 4;
        }
        if(title.name === 'assistant'){
            userAssistant = 1;
        }
        if(title.name === 'general'){
            userGeneral = 2;
        }
        if(title.name === 'senior'){
            userSenior = 3;
        }
    }
   
}

async function getCommittees() {
    let mainNav = document.getElementById('mainNav');
    let url = baseUrl + '/committees';
    let response = await fetch(url);
    if(response.status === 200){
        let committees = await response.json();
        
        let committeelst = document.createElement('ol');
        for(let committee of committees){
            let li = document.createElement('li');
            li.innerHTML = committee.committee_name;
            committeelst.appendChild(li);
        }
        mainNav.innerHTML ="Genres include:";
        mainNav.appendChild(committeelst);
    }else{
        mainNav.innerHTML = 'HI!';
    }
}
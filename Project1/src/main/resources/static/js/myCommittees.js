checkLogin().then(populateCommittees);
// async function getPitches(){
//     let url = baseUrl + '/users/pitches/';
//     let response = await fetch(url);
//     if(response.status === 200) {
//         let requests = await response.json();
//         populatePitches(requests);
//     }
// }
let poppitch = document.getElementById('poppitch');
function populateCommittees() {
    let comms = loggedUser.committees;
    let committeeSection = document.getElementById('commSection');

    if (comms.length > 0) {
        let table = document.createElement('table');
        table.innerHTML = `
            <tr>
                <th>Committees</th>

            </tr>
        `;

        for (let com of comms) {
            let tr = document.createElement('tr');
            tr.innerHTML = `
                <button id="${com.committee_name}Pitches" onclick = "${com.committee_name}Pitches()" type="button">
                    ${com.committee_name}
                </button>
            `

            ;
          
            table.appendChild(tr);
        } //end for

        committeeSection.appendChild(table);
    } else {
        committeeSection.innerHTML = 'Editors\' committees';
    }//else of if
 }//end populate committees 
    function fantasyPitches(){
        let url = baseUrl + '/pitch/committees/1'
        populatePitchSection(url);    
    }
    
    function sci_fiPitches(){
        let url = baseUrl + '/pitch/committees/2'
        populatePitchSection(url);
    }
    function fictionPitches(){
        let url = baseUrl + '/pitch/committees/3'
        populatePitchSection(url);
    }
    function historyPitches(){
        let url = baseUrl + '/pitch/committees/4'
        populatePitchSection(url);
    }
    function cookingPitches(){
        let url = baseUrl + '/pitch/committees/5'
        populatePitchSection(url);
    }
    function biographyPitches(){
        let url = baseUrl + '/pitch/committees/6'
        populatePitchSection(url);
    }
    function sportsPitches(){
        let url = baseUrl + '/pitch/committees/7'
        populatePitchSection(url);
    }
    function kidsPitches(){
        let url = baseUrl + '/pitch/committees/8'
        populatePitchSection(url);
    }


//end url switcher function set idk whate else to call this area
    async function populatePitchSection(url){
        poppitch.innerHTML = "";
        let response = await fetch(url);
        if(response.status === 200){
            let genPitches = await response.json();

            if (genPitches.length > 0) {
                let table = document.createElement('table');
                table.innerHTML = `
                    <tr>
                        <th>ID</th>
                        <th> Author ID </th>
                        <th>Story Title</th>
                        <th>Story Type</th>
                        <th>Genre</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Priority</th>
                        <th>Stage</th>
                        <th>Finish Date</th>
                    </tr>
                `;
                let highPriority = false;
                for(let priChk of genPitches){
                    if(priChk.priority.name == 'High Priority')
                        highPriority = true;
                }


                for (let pitch of genPitches) {
                    
                    if(!highPriority){
                    let tr = document.createElement('tr');
                    if(pitch.priority.name != 'zero'){
                        if(pitch.stage.id == userAssistant || pitch.stage.id == userGeneral || pitch.stage.id == userSenior) {
                                    tr.innerHTML = `
                                        <td id= "pitchIdNum${pitch.id}">${pitch.id}</td>

                                        <td id = "pitchAuthorId${pitch.id}>${pitch.author}</td>

                                        <td >${pitch.story_title}</td>
                                        
                                        <td id="pitchStoryTypeNum${pitch.id}">${pitch.story_type.name}</td>
                                        <td>${pitch.genre.name}</td>
                                        <td>${pitch.description}</td>
                                        <td id="pitchStatusNum${pitch.id}">${pitch.status.name}</td>
                                        <td id="pitchPriorityNum${pitch.id}">${pitch.priority.name}</td>
                                        <td id="pitchStageNum${pitch.id}">${pitch.stage.name}</td>
                                        <td>${pitch.finish_date}</td>
                                        <td><button id="accept" type="button" value="${pitch.id}" 
                                        onclick="acceptPitch(${pitch.id})">accept 
                                        </button></td>

                                        <td><button id="reject" type="button" value="${pitch.id}" 
                                        onclick="rejectPitch(${pitch.id})">reject 
                                        </button></td>

                                        <td><button id="request" type="button" onclick="setRequest(${pitch.id})">request 
                                        </button></td>
                                    `;
                  
                                     table.appendChild(tr);
                        }//check user titles to pitch stage
                    }//end priority level zero check
                }else if(highPriority){
                        let tr = document.createElement('tr');
                        if(pitch.priority.name != 'zero' && pitch.priority.name != 'normal'){
                            if(pitch.stage.id == userAssistant || pitch.stage.id == userGeneral || pitch.stage.id == userSenior) {
                                        tr.innerHTML = `
                                            <td id= "pitchIdNum${pitch.id}">${pitch.id}</td>
    
                                            <td id = "pitchAuthorId${pitch.id}>${pitch.author}</td>
    
                                            <td >${pitch.story_title}</td>
                                            
                                            <td id="pitchStoryTypeNum${pitch.id}">${pitch.story_type.name}</td>
                                            <td>${pitch.genre.name}</td>
                                            <td>${pitch.description}</td>
                                            <td id="pitchStatusNum${pitch.id}">${pitch.status.name}</td>
                                            <td id="pitchPriorityNum${pitch.id}">${pitch.priority.name}</td>
                                            <td id="pitchStageNum${pitch.id}">${pitch.stage.name}</td>
                                            <td>${pitch.finish_date}</td>
                                            <td><button id="accept" type="button" value="${pitch.id}" 
                                            onclick="acceptPitch(${pitch.id})">accept 
                                            </button></td>
    
                                            <td><button id="reject" type="button" value="${pitch.id}" 
                                            onclick="rejectPitch(${pitch.id})">reject 
                                            </button></td>
    
                                            <td><button id="request" type="button" onclick="setRequest(${pitch.id})">request 
                                            </button></td>
                                        `;
                      
                                         table.appendChild(tr);
                            }//check user titles to pitch stage
                        }//end priority level zero check
                    }
                } //end for
                
                poppitch.appendChild(table);
            } else {
                poppitch.innerHTML = 'No Pitches found for this Committee';
                
            }//else of if
            
        }else{
            poppitch.innerHTML = 'No Pitches found for this Committee';
                
        }//end if staus 200

    }//end pop pitch section asnyc funtion stuffs

async function acceptPitch(number){

    let data = number;
    let url = baseUrl + '/stage/' + number;
    let response = await fetch(url, {method: 'PUT', body:JSON.stringify(data)});
    if(response.status >= 200 && response.status < 300){
        console.log("yup");
        document.location.reload();
    }else{
        alert("Something may have happened and the accept was not processed");
    }
}
async function rejectPitch(number){
    if(confirm("Are you sure you want to delete pitch #" + number + "?")){
        let data ={
            id: document.getElementById(`pitchIdNum${number}`).innerHTML,
            story_type:{
                id : 5
            },
            status:{
                id : 3
            },
            priority:{
                id: 3
            },
            stage:{
                id: 5
            }
        };
        let url = baseUrl + '/pitch/'+number;
        let response = await fetch(url, {method: 'PUT', body: JSON.stringify(data)});
        if(response.status >= 200 && response.status < 300){
            console.log("yup");
            document.location.reload();
        }//check status
    }
   
}
    function setRequest(number){
        let reqSec = document.getElementById("requestForm");
        // let questionInput = document.createElement('input');
        // input.type = 'text';
        // input.id = 'quest';
        // input.placeholder = 'Question or request for Author';
        // reqSec.appendChild(questionInput);
        reqSec.innerHTML =
        `
        <input type='text' id='question' placeholder= 'What are you asking for?'><br>
        <td><button id="questId" type="button" onclick="requestPitch(${number})">Send request 
        </button></td>
        `;
    }


async function requestPitch(number){
    let url = baseUrl +'/pitch/'+ number;
    let response = await fetch(url)
    let thisPitch = await response.json();
    let quest = document.getElementById('question').value;


    if(confirm("ask the author: "+ quest)){
        let data = {
            recipient: {
                id: thisPitch.author
            },
            sender: {
                id: loggedUser.id
            },
            pitch:{id: number},
            question: document.getElementById('question').value
        };
        let nexturl = baseUrl + '/requests';
        let burr = await fetch(nexturl, {method: 'POST', body:JSON.stringify(data)});
        if(burr.status >= 200 && burr.status < 300){
            alert("request sent")
            document.location.reload();
        }else{
            alert("Something may have happened and the accept was not processed");
        }//end response
    }
}//end function



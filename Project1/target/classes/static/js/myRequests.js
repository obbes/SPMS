checkLogin().then(getRequests);

async function getRequests(){
    let getUserUrl = baseUrl + "/users/requests/"+ loggedUser.id;
    let response = await fetch(getUserUrl, {method: 'GET'});
    if(response.status === 200){
        let userReqs = await response.json();
        populateRequests(userReqs);
    }
}

function populateRequests(userReqs){
    let reqSection = document.getElementById('reqSection');
    if(userReqs.length > 0){
        let table = document.createElement('table');
        table.innerHTML = `
        <tr>
            <th>Recipeint</th>
            <th> Sender</th>
            <th> From Pitch</th>
            <th> Question or Comment </th>
            <th> Answer or Response </th>
            <th></th>
        </tr>
        `;
    
        for(let req of userReqs) {
            let tr = document.createElement('tr');
            tr.innerHTML = `
                <td id= "recipient${req.id}" >${req.recipient.username}</td>
                <td id= "sender${req.id}" >${req.sender.username}</td>
                <td id= "pitch${req.id}" >${req.pitch.story_title}</td>
                <td id= "question${req.id}" >${req.question}</td>
                `;
                // if user is sender or no answer has been sent
                // if(loggedUser.id === req.sender.id){
                    
                // //else if the user is the recipient and can respond
                // }else 
                // alert("The User id" +loggedUser.id + "comapares to the rec id:"+req.recipient.id + "and then check to see if Waiting for an answer ==" + req.answer)
                if(loggedUser.id === req.recipient.id && req.answer == "Waiting for an answer"){
                    let td2 = document.createElement('td');
                    td2.innerHTML = ` <input type='text' id='answer${req.id}' placeholder= 'How do you respond?'>`;
                    tr.appendChild(td2);
                    let td3 = document.createElement('td');
                    td3.innerHTML = `
                    <button id="ansBtn${req.id}" onclick = "sendAns(${req.id})" type="button">
                        Send answer
                    </button>
                    `;
                    tr.appendChild(td3);
                } else{
                    let td1 = document.createElement('td');
                    td1.innerHTML = `<td id= "answer${req.id}" > ${req.answer} </td>`;
                    tr.appendChild(td1);
                }//end answer section and append the row to table
        table.appendChild(tr);    
        }//end for
        reqSection.appendChild(table);
    }//end if
    else{
        reqSection.innerHTML = "No Current Requests";
    }//end else

}//end function

async function sendAns(num){
// alert (document.getElementById(`answer${num}`).text);
    let data = {
        id: num,
        answer : document.getElementById(`answer${num}`).value
        };//end data
    let url = baseUrl + '/requests/'+num;
    let response = await fetch(url, { method: 'PUT', body: JSON.stringify(data)});
    if(response.status >= 200 && response.status < 300){
        alert("Your answer has been sent")
        document.location.reload();     
    }else{
        alert("not 20Xs status");
    }
}
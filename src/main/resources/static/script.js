document.addEventListener("DOMContentLoaded", function() {
    // That's not dynamic we are just checking data for existing User 1
    fetchUser(1);
});

function fetchUser(userId) {
    fetch(`/api/users/${userId}`)
        .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to fetch user ');
                    }
                })
        .then(user => {
                    // Assuming the user object has an accounts field
                    const accountId = user.id;

                    // Update the username display
                    document.getElementById('username').textContent = user.username;

                    // Now fetch and display account details
                    fetchAccountDetails(accountId);
                })
                .catch(error => console.error('Error fetching user details:', error));
}

function fetchAccountDetails(accountId) {
    fetch(`/api/accounts/${accountId}`)
        .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Failed to load account details');
                    }
                })
        .then(account => {
            document.getElementById('balance').textContent = account.balance;
            document.getElementById('accountNumber').textContent = account.accountNumber;

            document.getElementById('depositForm').setAttribute('data-account-id', accountId);
            document.getElementById('withdrawalForm').setAttribute('data-account-id', accountId);
            fetchTransactions(accountId);
        })
        .catch(error => console.error('Error fetching account details:', error));
}
document.getElementById('depositForm').addEventListener('submit', function(event) {
     event.preventDefault();
     submitTransaction('depositForm', 'deposit');
 });

 document.getElementById('withdrawalForm').addEventListener('submit', function(event) {
     event.preventDefault();
     submitTransaction('withdrawalForm', 'withdraw');
 });

function submitTransaction(formId, transactionType) {
    const form = document.getElementById(formId);
    const accountId = form.getAttribute('data-account-id');
    const amount = form.amount.value; // Using form's name attribute for amount

    fetch(`/api/accounts/${accountId}/${transactionType}?amount=${amount}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => response.ok ? response.json() : Promise.reject(`${transactionType} failed`))
    .then(data => {
        console.log(`${transactionType} successful:`, data);
        fetchAccountDetails(accountId);
    })
    .catch(error => {
        console.error(`Error during ${transactionType}:`, error);
    });
}




function fetchTransactions(accountId) {
    fetch(`/api/accounts/${accountId}/transactions`)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to load transactions');
            }
        })
        .then(transactions => {
            // Assuming you have an element with id 'transactionList' to display the list
            const transactionList = document.getElementById('transactionList');
            transactionList.innerHTML = ''; // Clear previous transactions

            // Iterate through the transactions and create list items
            transactions.forEach(transaction => {
                const listItem = document.createElement('li');
                listItem.textContent = `${transaction.type} - ${transaction.amount} - ${transaction.timestamp}`;
                transactionList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching transactions:', error));
}

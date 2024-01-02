document.addEventListener("DOMContentLoaded", function() {
    // That's not dynamic
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
                    console.log("Account ID:" + accountId);

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
        })
        .catch(error => console.error('Error fetching account details:', error));
}

function submitTransaction(formId, transactionType) {
    const form = document.getElementById(formId);
    const accountId = form.getAttribute('data-account-id');
    const amount = form.amount.value; // Using form's name attribute for amount

    fetch(`/api/accounts/${accountId}/${transactionType}?amount=${amount}`, {
        method: 'POST',
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

document.getElementById('depositForm').addEventListener('submit', function(event) {
    event.preventDefault();
    submitTransaction('depositForm', 'deposit');
});

document.getElementById('withdrawalForm').addEventListener('submit', function(event) {
    event.preventDefault();
    submitTransaction('withdrawalForm', 'withdraw');
});

// STILL GETTING 403
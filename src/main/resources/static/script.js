document.addEventListener("DOMContentLoaded", function() {
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

            // Attach the accountId to both transaction forms for later use
            document.getElementById('depositForm').setAttribute('data-account-id', accountId);
            document.getElementById('withdrawalForm').setAttribute('data-account-id', accountId);
        })
        .catch(error => console.error('Error fetching account details:', error));
}

function submitTransaction(accountId, amount, type) {
    const transactionData = {
        amount: amount,
        type: type
    };

    fetch(`/api/accounts/${accountId}/transactions`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(transactionData),
    })
    .then(response => {
        if(response.ok) {
            return response.json();
        } else {
            throw new Error('Transaction failed');
        }
    })
    .then(data => {
        console.log("Transaction successful:", data);
        // Refresh balance or transaction history as needed
        fetchAccountDetails(accountId); // Refresh account details
    })
    .catch(error => console.error('Error submitting transaction:', error));
}

document.getElementById('depositForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const form = event.target;
    const accountId = form.getAttribute('data-account-id');
    const amount = document.getElementById('depositAmount').value;
    submitTransaction(accountId, amount, 'DEPOSIT');
});

document.getElementById('withdrawalForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const form = event.target;
    const accountId = form.getAttribute('data-account-id');
    const amount = document.getElementById('withdrawalAmount').value;
    submitTransaction(accountId, amount, 'WITHDRAWAL');
});

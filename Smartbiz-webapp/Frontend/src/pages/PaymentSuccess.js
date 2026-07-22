export function PaymentSuccessPage() {
  return `
    <div class="result-container success">
      <div class="result-card card text-center">
        <div class="success-icon-anim">✅</div>
        <h2 class="mt-md">Payment Successful!</h2>
        <p class="text-secondary">Invoice #INV-2023-089 has been paid.</p>
        
        <div class="payment-details card mt-lg text-left">
          <div class="flex justify-between mb-sm">
            <span class="text-xs text-secondary">Amount Paid</span>
            <span class="font-bold">₹ 14,443.00</span>
          </div>
          <div class="flex justify-between mb-sm">
            <span class="text-xs text-secondary">Payment Mode</span>
            <span class="font-bold">UPI (Google Pay)</span>
          </div>
          <div class="flex justify-between">
            <span class="text-xs text-secondary">Transaction ID</span>
            <span class="font-bold text-xs">TXN9876543210</span>
          </div>
        </div>
        
        <div class="action-buttons mt-xl">
          <button class="btn btn-primary w-full mb-md">🖨️ Print Receipt</button>
          <button class="btn btn-outline w-full" onclick="window.location.hash='#dashboard'">Back to Dashboard</button>
        </div>
      </div>
    </div>
  `;
}

PaymentSuccessPage.init = () => {
  // Any specific initialization for the success page
  console.log('Payment Success initialized');
};

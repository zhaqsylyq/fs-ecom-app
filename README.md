Ecommerce platform (fullstack project) Spring boot 3, Angular 18, Tailwind CSS, PostgreSQL, Kinde

Key Features:
- Admin panel for products and categories
- Filter engine
- Angular SSR
- Stripe integration
- Hexagonal architecture (Backend)


 Need to create an .env file at the root of the ecom-backend folder with the following values :
KINDE_CLIENT_ID=<client-id>

KINDE_CLIENT_SECRET=<client-secret>

STRIPE_API_KEY=<stripe-api-key>

STRIPE_WEBHOOK_SECRET=<stripe-webhook-secret>



 Run the dev server (frontend):
npx nx serve ecom-frontend


 Run the dev server (backend):
npx nx serve ecom-backend


 Architecture Schema of the entire app:
![schema-ecommerce-app](https://github.com/user-attachments/assets/1c306bab-730a-4fcd-95ed-79437d2e0cd7)

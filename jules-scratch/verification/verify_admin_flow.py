import re
from playwright.sync_api import sync_playwright, Page, expect

def run(playwright):
    browser = playwright.chromium.launch(headless=True)
    context = browser.new_context()
    page = context.new_page()

    # 1. Login as Admin
    page.goto("http://localhost:5173/login")
    page.get_by_label("Email").fill("admin@example.com")
    page.get_by_label("Password").fill("admin123")
    page.get_by_role("button", name="Log In").click()

    # 2. Verify Admin Dashboard
    expect(page).to_have_url(re.compile(r"/admin"))
    expect(page.get_by_role("heading", name="Admin Dashboard")).to_be_visible()

    # Check for summary data cards
    expect(page.get_by_text("Total Users")).to_be_visible()
    expect(page.get_by_text("Total Orders")).to_be_visible()
    expect(page.get_by_text("Total Revenue")).to_be_visible()

    # 3. Navigate to User Management
    page.get_by_role("link", name="Users").click()

    # 4. Verify User Management Page
    expect(page).to_have_url(re.compile(r"/admin/users"))
    expect(page.get_by_role("heading", name="User Management")).to_be_visible()

    # Check for the user table and its headers
    expect(page.get_by_role("table")).to_be_visible()
    expect(page.get_by_role("columnheader", name="ID")).to_be_visible()
    expect(page.get_by_role("columnheader", name="Email")).to_be_visible()
    expect(page.get_by_role("columnheader", name="Admin")).to_be_visible()
    expect(page.get_by_role("columnheader", name="Actions")).to_be_visible()

    # 5. Screenshot
    page.screenshot(path="jules-scratch/verification/admin_user_management.png")

    browser.close()

with sync_playwright() as playwright:
    run(playwright)
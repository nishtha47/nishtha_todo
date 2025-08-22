package pages;

import java.util.List;
import java.util.stream.Collectors;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;

public class TodoPage {
    private Page page;

    public TodoPage(Page page) {
        this.page = page;
    }

    // ========================= API Scenario =========================

    public void navigateToApp(String url) {
    page.navigate(url);
}


    // ========================= BASIC CRUD =========================
    public void addTodo(String todoText) {
        Locator input = page.locator(".new-todo");
        input.fill(todoText);
        input.press("Enter");

        // Wait for UI update
        page.locator(".todo-list li").first()
            .waitFor(new Locator.WaitForOptions().setTimeout(10000));

        // Debug
        List<String> todos = page.locator(".todo-list li").allTextContents().stream()
                                 .map(String::trim)
                                 .toList();
        System.out.println("Todos after add: " + todos);
    }

    public boolean isTodoPresent(String todoText) {
        return page.locator(".todo-list li label")
                   .filter(new Locator.FilterOptions().setHasText(todoText))
                   .count() > 0;
    }

    public void completeFirstTodo() {
        page.locator(".todo-list li .toggle").first().click();
    }

    public boolean isFirstTodoCompleted() {
        return page.locator(".todo-list li").first().getAttribute("class").contains("completed");
    }

    public void editFirstTodo(String updatedText) {
        Locator firstTodo = page.locator(".todo-list li").first();
        firstTodo.dblclick();

        Locator editInput = firstTodo.locator(".edit");
        editInput.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        editInput.fill(updatedText);
        editInput.press("Enter");
    }

    public void deleteFirstTodo() {
        page.locator(".todo-list li").first().hover();
        page.locator(".todo-list li .destroy").first().click();
    }

    public int getTodoCount() {
        return page.locator(".todo-list li").count();
    }

    // ========================= NEGATIVE SCENARIOS =========================
    // Try adding empty todo
    public boolean addEmptyTodo() {
        Locator input = page.locator(".new-todo");
        input.fill("");
        input.press("Enter");
        return getTodoCount() == 0;
    }

    // Try adding spaces-only todo
    public boolean addSpacesTodo() {
        Locator input = page.locator(".new-todo");
        input.fill("    ");
        input.press("Enter");
        return getTodoCount() == 0;
    }

    // Try editing with empty text
    public boolean editFirstTodoToEmpty() {
        try {
            Locator firstTodo = page.locator(".todo-list li").first();
            firstTodo.dblclick();
            Locator editInput = firstTodo.locator(".edit");
            editInput.fill("");
            editInput.press("Enter");
            return !firstTodo.innerText().isEmpty(); // Should not save empty
        } catch (PlaywrightException e) {
            return true; // fails gracefully
        }
    }

    // Try deleting when no todos exist
    public boolean deleteWhenEmpty() {
        int before = getTodoCount();
        try {
            page.locator(".todo-list li .destroy").first().click();
        } catch (Exception e) {
            System.out.println("Handled delete on empty list gracefully.");
        }
        int after = getTodoCount();
        return before == after;
    }

    // ========================= PERFORMANCE / STRESS =========================
    // Add bulk todos for stress testing
    public void addBulkTodos(int count) {
        for (int i = 1; i <= count; i++) {
            page.locator(".new-todo").fill("Task " + i);
            page.locator(".new-todo").press("Enter");
        }
    }

    // Measure bulk add time
    public long measureBulkAddPerformance(int count) {
        long start = System.currentTimeMillis();
        addBulkTodos(count);
        page.waitForSelector(".todo-list li:nth-child(" + count + ")", 
            new Page.WaitForSelectorOptions().setTimeout(30000));
        long end = System.currentTimeMillis();
        return end - start;
    }

    // Stress test editing large input
    public boolean editFirstTodoWithLongText(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append("X");

        try {
            Locator firstTodo = page.locator(".todo-list li").first();
            firstTodo.dblclick();
            Locator editInput = firstTodo.locator(".edit");
            editInput.fill(sb.toString());
            editInput.press("Enter");
            return isTodoPresent(sb.toString());
        } catch (Exception e) {
            System.out.println("Editing with long text failed: " + e.getMessage());
            return false;
        }
    }

    // Validate performance under rapid adds
    public void addTodosRapidly(int count) {
        for (int i = 1; i <= count; i++) {
            try {
                page.locator(".new-todo").fill("QuickTask " + i);
                page.locator(".new-todo").press("Enter");
            } catch (PlaywrightException e) {
                System.out.println("Error while rapid adding task " + i + ": " + e.getMessage());
            }
        }
    }

      // ========================= TOGGLE METHODS =========================
    public void toggleTodo(int index) {
        page.locator(".todo-list li .toggle").nth(index).click();
    }

    public void toggleAllTodos() {
        page.locator(".toggle-all").click();
    }

    public boolean isTodoCompleted(int index) {
        return page.locator(".todo-list li").nth(index).getAttribute("class").contains("completed");
    }

    public List<Boolean> getAllTodosStatus() {
        return page.locator(".todo-list li").all().stream()
                   .map(t -> t.getAttribute("class").contains("completed"))
                   .collect(Collectors.toList());
    }

    // ========================= FILTER METHODS =========================
    public void filterTodos(String filter) {
        page.locator("ul.filters li a")
            .filter(new Locator.FilterOptions().setHasText(filter))
            .click();
    }

    public List<String> getTodosText() {
        return page.locator(".todo-list li label").allTextContents();
    }

}

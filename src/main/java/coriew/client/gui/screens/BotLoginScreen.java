package coriew.client.gui.screens;

import coriew.client.gui.widgets.PasswordFieldWidget;
import coriew.client.systems.networking.Networking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import javax.annotation.Nullable;

public class BotLoginScreen extends Screen {
    private static final Text ENTER_EMAIL_TEXT = new TranslatableText("Enter email");
    private static final Text ENTER_PASSWORD_TEXT = new TranslatableText("Enter password");
    private Text ERROR_MESSAGE_TEXT;
    private ButtonWidget loginButton;
    private TextFieldWidget emailField;
    private PasswordFieldWidget passwordField;
    private final Screen parent;

    public BotLoginScreen(Screen parent, @Nullable TranslatableText error) {
        super(new TranslatableText("MCCoreBot Login"));
        this.parent = parent;
        this.ERROR_MESSAGE_TEXT = error != null ? error :  new TranslatableText("");
    }

    public void tick() {
        this.emailField.tick();
        this.passwordField.tick();
    }

    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.emailField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, 66, 200, 20, new TranslatableText(""));
        this.emailField.setTextFieldFocused(true);
        this.emailField.setMaxLength(320);
        this.emailField.setChangedListener((serverName) -> {
            this.updateLoginButton();
        });
        this.addSelectableChild(this.emailField);
        this.passwordField = new PasswordFieldWidget(this.textRenderer, this.width / 2 - 100, 106, 200, 20, new TranslatableText(""));
        this.passwordField.setMaxLength(256);
        this.passwordField.setChangedListener((address) -> {
            this.updateLoginButton();
        });
        this.addSelectableChild(this.passwordField);
        this.loginButton = (ButtonWidget)this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20, new TranslatableText("Login"), (button) -> {
            this.loginAndClose();
        }));
        this.updateLoginButton();
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void resize(MinecraftClient client, int width, int height) {
        String string = this.passwordField.getText();
        String string2 = this.emailField.getText();
        this.init(client, width, height);
        this.passwordField.setText(string);
        this.emailField.setText(string2);
    }

    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    private void loginAndClose() {
        try {
            Networking.INSTANCE.SendLoginRequest(emailField.getText(), passwordField.getText());
            // Only close if no error is thrown, as that means successful login.
            close();
        } catch (Exception ex)
        { // If an error is thrown, set the error message text.
            ERROR_MESSAGE_TEXT = new TranslatableText(ex.getMessage());
        }
    }

    public void close() {
        this.client.setScreen(this.parent);
    }

    private void updateLoginButton() {
        this.loginButton.active = ServerAddress.isValid(this.passwordField.getText()) && !this.emailField.getText().isEmpty();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 17, 16777215);
        drawTextWithShadow(matrices, this.textRenderer, ENTER_EMAIL_TEXT, this.width / 2 - 100, 53, 10526880);
        drawTextWithShadow(matrices, this.textRenderer, ENTER_PASSWORD_TEXT, this.width / 2 - 100, 94, 10526880);
        drawTextWithShadow(matrices, this.textRenderer, ERROR_MESSAGE_TEXT, this.width / 2 - 100, 135, 16711680);
        this.emailField.render(matrices, mouseX, mouseY, delta);
        this.passwordField.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
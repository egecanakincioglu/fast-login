# ⚙️ FastLogin: AuthMe Integrated Fast Login Plugin for Java Minecraft

## 🚀 What is the Fast Login Plugin?

The Fast Login Plugin is an add-on that collaborates with the plugin handling your login information, enabling you to log in to the server more quickly. It's currently in beta, so there may be some bugs. We haven't been actively developing it for about 6 months. However, we plan to continue its development in our spare time, but for now, we wanted it to remain here as a project example.

## 🛠️ How Does the Fast Login Plugin Work?

The Fast Login Plugin works alongside the AuthMe Login plugin and requires the AuthMe plugin to function. Once a password is entered for an account, subsequent logins from the same IP address won't require re-entering the password, allowing for quick access. Of course, players who wish to disable this system can do so. The basic principle is to match the user's information with their IP address, and if the same user logs in from the same IP, automatic login is provided. In situations like resetting the modem or connecting from a different modem, where the IP address changes, the system will prompt for a password again, and the information will be matched with the last IP address you logged in from.

## 🛠️ Installation and Information About the Plugin

To install the plugin, simply place the files in the plugins directory. If you encounter any errors related to the plugin, you can report them to us. Since the plugin is still in beta, there may be a chance of errors. We postponed the development of the system we started about 6 months ago due to our decreased individual interest in Minecraft servers. However, you can access the full version of this plugin from this GitHub account in the future.

- To install the plugin on your server, first navigate to the ./plugins directory of your server and install the AuthMe plugin. If you already have the AuthMe plugin on your server, you can skip this step.
- After installing AuthMe, place the CartelFastLogin.jar plugin file into the ./plugins directory and start the server, then monitor the console.
- When the plugins are loaded, a connection will be established between CartelFastLogin and AuthMe. Once the plugin is loaded, log in to the server, and your information will be matched with your IP address the first time you log in.
- Since the matching process is automatic, you don't need to do anything extra.
- If you don't want automatic login, you can disable it by typing /autologin and toggling it off from the menu.

If you're interested in collaborating on different projects with us, you can reach out to me through the homepage of my GitHub account. Also, if you have any suggestions, recommendations, or bug reports, feel free to contact us.

# EasyMessage

The EasyMessage Library offers an easy way to create dialogs in an app.
It also makes it possible to show the dialog only at the first start of an app or after every version change, which can be used to show an EULA, changelogs etc.
With EasyMessage you can...


* create dialogs quickly without worrying too much about the details of the implementation
* show a dialog **every time**, only on the **first start** or after every **version change**
* decide whether the user has to accept the dialog in order to continue using your app or not
* change the **size** and **gravity** of the title and the text
* change the text of the buttons to what you like

# How to use

You can create a new EasyMessage by calling its constructor:

  EasyMessage em = new EasyMessage();

You can then define the title, text and behaviour of the dialog: 

```
  em.setMessageTitle("My title");
  em.setMessageText(R.string.my_message_text, this);
  em.setTitleSize(30);
  em.setMessageSize(20);
  em.setTitleGravity(Gravity.CENTER);
  em.setCloseIfNotAccepted(true);
  em.includeNegativeButton(true);
  em.setPositiveButtonText("Yes");
  em.setNegativeButtonText("hell no");
```

To show the dialog, define an ID for it and just call one of the following methods.

```
  em.show(this, "myMessageId"); <br>
  em.showOnFirstStart(this, "myMessageId"); <br>
  em.showAfterVersionChange(this, "myMessageId"); <br>
```

# License

Copyright 2018 Robin Pape

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

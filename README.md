# EasyMessage

The EasyMessage Library offers an easy way to create dialogs in an app. <br>
It also makes it possible to show the dialog only at the first start of an app or after every version change, which can be used to show an EULA, changelogs etc. <br>
With EasyMessage you can... <br>

<ul>
  <li> create dialogs quickly without worrying too much about the details of the implementation </li>
  <li> show a dialog <b>every time</b>, only on the <b>first start</b> or after every <b>version change</b> </li>
  <li> decide whether the user has to accept the dialog in order to continue using your app or not </li>
  <li> change the size and gravity of the title and the text </li>
  <li> change the text of the buttons to what you like </li>
</ul>

# How to use

You can create a new EasyMessage by calling its constructor:

<code>
  EasyMessage em = new EasyMessage();
</code>

You can then define the title, text and behaviour of the dialog:

<code>
  em.setMessageTitle("My title");
  em.setMessageText(R.string.my_message_text, this);
  em.setTitleSize(30);
  em.setMessageSize(20);
  em.setTitleGravity(Gravity.CENTER);
  em.setCloseIfNotAccepted(true);
  em.includeNegativeButton(true);
  em.setPositiveButtonText("Yes");
  em.setNegativeButtonText("hell no");
</code>

To show the dialog, define an ID for it and just call one of the following methods.

<code>
  em.show(this, "myMessageId");
  em.showOnFirstStart(this, "myMessageId");
  em.showAfterVersionChange(this, "myMessageId");
</code>

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

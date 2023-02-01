;This file will be executed next to the application bundle image
;I.e. current directory will contain folder IntegrationProject-1.0 with application files
[Setup]
AppId={{SOME-GUID-USED-FOR-UPDATE-DETECTION}}
AppName=IntegrationProject-1.0
AppVersion=1.0
AppVerName=IntegrationProject-1.0 1.0
AppPublisher=gustavo
AppComments=IntegrationProject-1.0
AppCopyright=Copyright (C) 2023
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={localappdata}\IntegrationProject-1.0
DisableStartupPrompt=Yes
DisableDirPage=Yes
DisableProgramGroupPage=Yes
DisableReadyPage=Yes
DisableFinishedPage=Yes
DisableWelcomePage=Yes
DefaultGroupName=gustavo
;Optional License
LicenseFile=
;WinXP or above
MinVersion=6
OutputBaseFilename=IntegrationProject-1.0-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=IntegrationProject-1.0\IntegrationProject-1.0.ico
UninstallDisplayIcon={app}\IntegrationProject-1.0.ico
UninstallDisplayName=IntegrationProject-1.0
WizardImageStretch=No
WizardSmallImageFile=IntegrationProject-1.0-setup-icon.bmp   
ArchitecturesInstallIn64BitMode=


[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "IntegrationProject-1.0\IntegrationProject-1.0.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "IntegrationProject-1.0\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\IntegrationProject-1.0"; Filename: "{app}\IntegrationProject-1.0.exe"; IconFilename: "{app}\IntegrationProject-1.0.ico"; Check: returnTrue()
Name: "{commondesktop}\IntegrationProject-1.0"; Filename: "{app}\IntegrationProject-1.0.exe";  IconFilename: "{app}\IntegrationProject-1.0.ico"; Check: returnFalse()

[Run]
Filename: "{app}\IntegrationProject-1.0.exe"; Description: "{cm:LaunchProgram,IntegrationProject-1.0}"; Flags: nowait postinstall skipifsilent; Check: returnTrue()
Filename: "{app}\IntegrationProject-1.0.exe"; Parameters: "-install -svcName ""IntegrationProject-1.0"" -svcDesc ""IntegrationProject-1.0"" -mainExe ""IntegrationProject-1.0.exe""  "; Check: returnFalse()

[UninstallRun]
Filename: "{app}\IntegrationProject-1.0.exe "; Parameters: "-uninstall -svcName IntegrationProject-1.0 -stopOnUninstall"; Check: returnFalse()

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  

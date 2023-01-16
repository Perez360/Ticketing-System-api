package com.example.shared.html


fun generatehtmlTemplate(firstName: String, token: String?): String {
    return """
        <!DOCTYPE html>
        <html lang="en">
          <head>
            <meta charset="UTF-8" />
            <meta http-equiv="X-UA-Compatible" content="IE=edge" />
            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
            <title>Verify</title>
            <style>
              .container {
                width: fit-content;
                margin: 100px auto;
                border-radius: 30px;
                text-align: center;
                padding: 80px;
                font-family: 'Segoe UI';
                box-shadow: 0px 0px 10px rgb(136, 136, 136);
              }

            .title{
              color: rgb(255, 81, 0);
              font-size: 40px;
            }

              .body {
                align-self: center;
              }

              .token-box {
                border: 3px rgb(119, 119, 119) solid;
                padding: 10px 25px;
                font-size: large;
                font-weight: bold;
              }
            </style>
          </head>
          <body>
            <div class="container">
              <div class="head">
                <h1 class="title">Verify Account</h1>
                <p>Hi ${firstName}, You have requested to verify your account.</p>
              </div>
              <div class="body">
                <p>Use the token below to verify your account:</p>

                <div class="token-box">
                 $token
                </div>
              </div>
            </div>
          </body>
        </html>

    """
}
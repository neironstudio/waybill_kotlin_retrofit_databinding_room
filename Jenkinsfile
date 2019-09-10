pipeline {

    agent {
        node {
            label 'ubuntu_slave'
            }
        }


    environment {
        PATH="GRADLE_HOME=/opt/gradle/gradle-5.4.1:$PATH"
        start_message = "STARTED: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>"
        successful_message = "SUCCESSFUL: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]\n<${env.BUILD_URL}|Open>"
        }


    stages {

        stage('Create local.properties') {
            steps {
            sh 'echo "sdk.dir=/opt/android-sdk-linux" >> local.properties'
            }
        }

        stage("Clean apk") {
            steps {
                sh '/opt/gradle/gradle-5.4.1/bin/gradle clean'
                }
            }

        stage("Build project") {
            steps {
                sh '/opt/gradle/gradle-5.4.1/bin/gradle build'
                }
            }

        stage("Assemble debug apk") {
            steps {
                sh '/opt/gradle/gradle-5.4.1/bin/gradle assembleDebug'
                }
            }

        /*TODO: add sign
        stage("Assemble release apk") {
            steps {
                sh '/opt/gradle/gradle-5.4.1/bin/gradle assembleRelease'
                }
            }*/

        stage("Move & rename apk") {
            steps {
                sh '''#!/bin/bash
                APK_PATH=app/build/outputs/apk
                mv ${APK_PATH}/debug/app-debug.apk waybill_debug.apk
                '''
                }
                // mv ${APK_PATH}/release/app-release.apk waybill_release.apk
            }
    }

    post {
        success {
          archiveArtifacts "waybill_debug.apk"
          // , waybill_release.apk
        }

        /*TODO: email
        failure {

        }*/
      }
  }
